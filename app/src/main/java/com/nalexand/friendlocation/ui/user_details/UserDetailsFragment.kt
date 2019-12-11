package com.nalexand.friendlocation.ui.user_details

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.base.BaseFragment
import com.nalexand.friendlocation.model.recycler.UserItem
import com.nalexand.friendlocation.ui.user_details.animations.UserDetailsStartAnimation
import com.nalexand.friendlocation.utils.UserBinder
import com.nalexand.friendlocation.utils.animator.BaseAnimationListener
import com.nalexand.friendlocation.utils.extensions.observe
import com.nalexand.friendlocation.utils.extensions.printBackStack
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlinx.android.synthetic.main.info_holder.view.*
import kotlinx.android.synthetic.main.user_item.*

class UserDetailsFragment : BaseFragment<UserDetailsViewModel>(
	UserDetailsViewModel::class.java,
	R.layout.fragment_user_details
) {

	override fun initializeUi() {
		cvUserInfoName.txvInfoHolderTitle.text = getString(R.string.name)
	}

	override fun initializeObservers() {
		observe(viewModel.user) {
			UserBinder.bindTo(cvUserItem, UserItem().render(it, UserBinder.getParams(it.host)))
		}
		observe(viewModel.userName) { name ->
			cvUserInfoName.txvInfoHolderContent.text = name ?: "?"
		}
	}

	override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
		return if (nextAnim != 0) {
			AnimationUtils.loadAnimation(context, nextAnim).apply {
				setAnimationListener(BaseAnimationListener(enter,
					UserDetailsStartAnimation(clUserDetailsContent, getComposite())
				))
			}
		} else {
			cvUserInfoName.translationY = 0f
			fabAddNote.translationY = 0f
			null
		}
	}
}