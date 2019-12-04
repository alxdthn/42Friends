package com.nalexand.friendlocation.di.modules

import com.nalexand.friendlocation.network.service.IntraAuthService
import com.nalexand.friendlocation.network.service.IntraUserService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
object IntraServiceModule {

    @Provides
    @Singleton
    internal fun provideIntraUserService(
        @Named("MainRetrofit")
        retrofit: Retrofit
    ): IntraUserService {
        return retrofit.create(IntraUserService::class.java)
    }

    @Provides
    @Singleton
    internal fun provideIntraAuthService(
        @Named("AuthRetrofit")
        retrofit: Retrofit
    ): IntraAuthService {
        return retrofit.create(IntraAuthService::class.java)
    }
}