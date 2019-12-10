package com.nalexand.friendlocation.di.modules

import com.nalexand.friendlocation.network.IntraAuthenticator
import com.nalexand.friendlocation.network.IntraInterceptor
import com.nalexand.friendlocation.network.service.IntraAuthService
import com.nalexand.friendlocation.repository.app.AppPreferences
import com.nalexand.friendlocation.utils.AppConstants.BASE_URL
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
object NetworkModule {

	@Provides
	@Singleton
	@Named("MainRetrofit")
	internal fun provideRetrofit(client: OkHttpClient): Retrofit {
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.client(client)
			.addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

	@Provides
	@Singleton
	@Named("AuthRetrofit")
	internal fun provideAuth(): Retrofit {
		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

	@Provides
	@Singleton
	internal fun provideOkHttpClient(
		interceptor: IntraInterceptor,
		service: IntraAuthService,
		preferences: AppPreferences
	): OkHttpClient {
		return OkHttpClient.Builder()
			.addInterceptor(interceptor)
			.authenticator(IntraAuthenticator(service, preferences))
			.callTimeout(100, TimeUnit.SECONDS)
			.connectTimeout(100, TimeUnit.SECONDS)
			.readTimeout(100, TimeUnit.SECONDS)
			.writeTimeout(100, TimeUnit.SECONDS)
			.build()
	}
}