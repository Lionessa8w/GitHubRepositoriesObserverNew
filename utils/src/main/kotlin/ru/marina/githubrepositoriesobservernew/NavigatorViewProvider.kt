package ru.marina.githubrepositoriesobservernew

import androidx.fragment.app.Fragment

interface NavigatorViewProvider {

    fun getViewId(): Int

    fun getRepositoryInfoFragment(name: String, owner: String): Fragment

    fun getAuthUserFragment(): Fragment
    fun getRepositoriesListFragment(): Fragment

}