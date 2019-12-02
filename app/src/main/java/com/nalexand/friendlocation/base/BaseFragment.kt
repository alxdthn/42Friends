package com.nalexand.friendlocation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nalexand.friendlocation.main.MainActivity
import com.nalexand.friendlocation.main.App
import com.nalexand.friendlocation.ui.add_member.AddMemberFragment
import com.nalexand.friendlocation.ui.home.HomeFragment
import com.nalexand.friendlocation.ui.notes.NotesFragment
import javax.inject.Inject

abstract class BaseFragment(private val layout: Int) : Fragment() {

    @Inject
    lateinit var viewMoldelFactory: ViewModelProvider.Factory

    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = activity as MainActivity
        inject()
        return inflater.inflate(layout, container, false)
    }

    private fun inject() {
        val injector = (mainActivity.application as App).getAppComponent()

        when (this) {
            is HomeFragment -> injector.inject(this)
            is NotesFragment -> injector.inject(this)
            is AddMemberFragment -> injector.inject(this)
        }
    }
}