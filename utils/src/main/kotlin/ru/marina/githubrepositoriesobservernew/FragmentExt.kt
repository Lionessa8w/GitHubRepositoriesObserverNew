package ru.marina.githubrepositoriesobservernew

import androidx.fragment.app.Fragment

fun Fragment.getNavigatorFragment() : NavigatorActivity? {
    return this.activity as? NavigatorActivity
}