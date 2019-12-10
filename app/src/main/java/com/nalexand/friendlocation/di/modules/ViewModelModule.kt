package com.nalexand.friendlocation.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nalexand.friendlocation.di.ViewModelFactory
import com.nalexand.friendlocation.di.key.ViewModelKey
import com.nalexand.friendlocation.main.CommonViewModel
import com.nalexand.friendlocation.ui.add_user.AddUserViewModel
import com.nalexand.friendlocation.ui.home.HomeViewModel
import com.nalexand.friendlocation.ui.user_details.UserDetailsViewModel
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
	@ViewModelKey(CommonViewModel::class)
	internal abstract fun bindCommonViewModel(viewModel: CommonViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(HomeViewModel::class)
	internal abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(AddUserViewModel::class)
	internal abstract fun bindAddUserViewModel(viewModel: AddUserViewModel): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(UserDetailsViewModel::class)
	internal abstract fun bindDetailsViewModel(viewModel: UserDetailsViewModel): ViewModel
}