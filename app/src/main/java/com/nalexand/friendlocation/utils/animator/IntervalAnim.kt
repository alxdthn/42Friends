package com.nalexand.friendlocation.utils.animator

import android.util.Log
import android.view.animation.AccelerateInterpolator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.math.absoluteValue

fun reducedInterval(period: Long, reducePercent: Float, unit: TimeUnit): Observable<Long> {
	return Observable.create<Long> { emitter ->
		var currentPeriod = period
		while (true) {
			if (emitter.isDisposed) break
			emitter.onNext(currentPeriod)
			unit.sleep(currentPeriod)
			currentPeriod -= (currentPeriod * reducePercent).toLong()
		}
	}.subscribeOn(Schedulers.computation())
}

fun Iterable<AnimParams>.intervalAnim(
	duration: Long,
	interpolator: Interpolator = LinearInterpolator(),
	anim: (AnimParams) -> Disposable
): Observable<Disposable> {
	val viewsObservable = Observable.fromIterable(this)
	val interpolateInterval = Observable.create<Long> { emitter ->
		var summary = 0L
		val size = count()
		val sleeps = mutableListOf<Long>()

		for (counter in 1..size) {
			if (emitter.isDisposed) break
			val percent = counter.toFloat() / size.toFloat()
			val interpolated = interpolator.getInterpolation(percent)
			val sleepTime = (duration * interpolated - summary).toLong()
			summary += sleepTime
			sleeps.add(sleepTime)
		}
		for (counter in size - 1 downTo 0) {
			val sleepTime = sleeps[counter]
			emitter.onNext(sleepTime)
			try {
				Thread.sleep(sleepTime)
			} catch (e: InterruptedException) { Unit }
		}
	}.subscribeOn(Schedulers.computation())

	return Observable.zip(interpolateInterval, viewsObservable,
		BiFunction<Long, AnimParams, Disposable> { _, params ->
			anim(params)
		})
}

/*
Log.d("bestTAG", "$counter/$size IN: ${String.format("%-10f", percent)}" +
						"OUT: ${String.format("%-10f", interpolated)}" +
						"SLP: ${String.format("%-5d", sleepTime)}" +
						"SUM: $summary"
			)
 */

/*
	Log.d("bestTAG", "IN: ${String.format("%-10f", percent)}" +
			"OUT: ${String.format("%-10f", interpolated)}" +
			"DIF: ${String.format("%-10f", interpolatedDiff)}" +
			"SLP: $sleepTime " +
			"CUR: $currentDuration")
 */