package com.nalexand.friendlocation.di.component

import android.app.Application
import com.nalexand.friendlocation.di.modules.*
import com.nalexand.friendlocation.main.MainActivity
import com.nalexand.friendlocation.ui.add_member.AddMemberFragment
import com.nalexand.friendlocation.ui.home.HomeFragment
import com.nalexand.friendlocation.ui.notes.NotesFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        StorageModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        IntraServiceModule::class,
        DataBaseModule::class
    ]
)
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(homeFragment: HomeFragment)

    fun inject(addMemberFragment: AddMemberFragment)

    fun inject(notesFragment: NotesFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}