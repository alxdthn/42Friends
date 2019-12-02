package com.nalexand.friendlocation.di.modules

import com.nalexand.friendlocation.network.service.IntraService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object IntraServiceModule {

    @Provides
    @Singleton
    internal fun provideIntraUserService(retrofit: Retrofit): IntraService {
        return retrofit.create(IntraService::class.java)
    }
}