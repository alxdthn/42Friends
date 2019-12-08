package com.nalexand.friendlocation.di.modules

import androidx.lifecycle.ViewModel
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel
import com.nalexand.friendlocation.ui.home.HomeViewModel
import com.nalexand.friendlocation.ui.notes.NotesViewModel
import dagger.Binds
import dagger.Module

@Suppress("unused")
@Module
abstract class ViewModelModule {

	@Binds
	internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

	@Binds
	internal abstract fun bindAddUserViewModel(viewModel: AddUserViewModel): ViewModel

	@Binds
	internal abstract fun bindNotesViewModel(viewModel: NotesViewModel): ViewModel
}