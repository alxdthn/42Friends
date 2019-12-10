package com.nalexand.friendlocation.di.component

import android.app.Application
import com.nalexand.friendlocation.di.modules.*
import com.nalexand.friendlocation.main.MainActivity
import com.nalexand.friendlocation.ui.add_user.AddUserFragment
import com.nalexand.friendlocation.ui.home.HomeFragment
import com.nalexand.friendlocation.ui.user_details.UserDetailsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        StorageModule::class,
        NetworkModule::class,
        IntraServiceModule::class,
        DataBaseModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(homeFragment: HomeFragment)

    fun inject(userDetailsFragment: UserDetailsFragment)

    fun inject(AddUserFragment: AddUserFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}