package com.nalexand.friendlocation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nalexand.friendlocation.MainActivity
import com.nalexand.friendlocation.main.App
import com.nalexand.friendlocation.ui.home.HomeFragment

abstract class BaseFragment(private val layout: Int) : Fragment() {

    val mainActivity = activity as MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    private fun inject() {
        val injector = (mainActivity.application as App).getAppComponent()

        when (this) {
            is HomeFragment -> injector.inject(this)
        }
    }
}