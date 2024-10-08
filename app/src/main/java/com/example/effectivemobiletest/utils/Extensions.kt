package com.example.effectivemobiletest.utils

import android.app.Activity
import android.view.View
import androidx.navigation.NavOptions
import com.example.effectivemobiletest.R
import com.example.effectivemobiletest.presentation.MainActivity

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Activity.hideTabs() {
    val mainActivity = this as MainActivity
    mainActivity.binding.bottomNav.hide()
}

fun Activity.showTabs() {
    val mainActivity = this as MainActivity
    mainActivity.binding.bottomNav.show()
}

fun navOptions(): NavOptions {
    //animation for navigating pages
    return NavOptions.Builder()
        .setEnterAnim(R.anim.slide_enter)
        .setExitAnim(R.anim.slide_exit)
        .setPopEnterAnim(R.anim.slide_pop_enter)
        .setPopExitAnim(R.anim.slide_pop_exit)
        .build()
}