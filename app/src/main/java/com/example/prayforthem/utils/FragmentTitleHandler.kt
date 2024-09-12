package com.example.prayforthem.utils

import com.example.prayforthem.RootActivity

fun setFragmentTitle(activity: RootActivity, fragmentTitle: String) {
    activity.rootBinding.toolbar.title = fragmentTitle
}