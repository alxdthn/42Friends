package com.nalexand.friendlocation.utils

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.nalexand.friendlocation.model.recycler.Item
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class AppDiffUtil {

	private var source: List<Item>? = null
	private var renderer: ((Iterable<Any>) -> List<Item>)? = null
	private var diffCallback: BaseDiffCallback? = null
	private var diffSubject: PublishSubject<DiffUtil.DiffResult>? = null
	private var result: List<Item>? = null
	private var onResult: ((List<Item>) -> Unit)? = null

	fun setSource(source: List<Item>): AppDiffUtil {
		this.source = source
		return this
	}

	fun setRenderer(renderer: (Iterable<Any>) -> List<Item>): AppDiffUtil {
		this.renderer = renderer
		return this
	}

	fun setCallback(callback: BaseDiffCallback): AppDiffUtil {
		this.diffCallback = callback
		return this
	}

	fun dispatchTo(subject: PublishSubject<DiffUtil.DiffResult>): AppDiffUtil {
		this.diffSubject = subject
		return this
	}

	fun onResult(block: (items: List<Item>) -> Unit): AppDiffUtil {
		this.onResult = block
		return this
	}

	fun calculate(data: Iterable<Any>): Disposable {
		validate()
		return Observable.fromCallable { renderer!!(data) }
			.subscribeOn(Schedulers.computation())
			.map { newItems ->
				if (onResult != null) result = newItems
				DiffUtil.calculateDiff(diffCallback!!.build(source!!, newItems), true)
			}
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe { diffResult ->
				onResult?.invoke(result!!)
				diffSubject?.onNext(diffResult)
			}
	}

	private fun validate() {
		when {
			source == null -> throw IllegalArgumentException("source can be initialized")
			renderer == null -> throw IllegalArgumentException("renderer can be initialized")
			diffCallback == null -> throw IllegalArgumentException("callback can be initialized")
		}
	}

	open class BaseDiffCallback : DiffUtil.Callback() {

		private lateinit var oldItems: List<Item>
		private lateinit var newItems: List<Item>

		override fun getOldListSize() = oldItems.size

		override fun getNewListSize() = newItems.size

		override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
			return oldItems[oldItemPosition].id == newItems[newItemPosition].id
		}

		override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
			return oldItems[oldItemPosition] == newItems[newItemPosition]
		}

		fun getOldItems(): List<Item> = oldItems

		fun getNewItems(): List<Item> = newItems

		fun build(oldItems: List<Item>, newItems: List<Item>): BaseDiffCallback {
			this.oldItems = oldItems
			this.newItems = newItems
			return this
		}
	}
}