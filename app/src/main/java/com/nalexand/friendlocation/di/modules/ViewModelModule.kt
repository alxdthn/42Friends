package com.nalexand.friendlocation.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nalexand.friendlocation.di.ViewModelFactory
import com.nalexand.friendlocation.di.key.ViewModelKey
import com.nalexand.friendlocation.ui.add_member.AddMemberViewModel
import com.nalexand.friendlocation.ui.home.HomeViewModel
import com.nalexand.friendlocation.ui.notes.NotesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

	@Binds
	internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

	@Binds
	@IntoMap
	@ViewModelKey(HomeViewModel::class)
	internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(AddMemberViewModel::class)
	internal abstract fun bindAddMemberViewModel(viewModel: AddMemberViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(NotesViewModel::class)
	internal abstract fun bindAddNotesViewModel(viewModel: NotesViewModel): ViewModel
}