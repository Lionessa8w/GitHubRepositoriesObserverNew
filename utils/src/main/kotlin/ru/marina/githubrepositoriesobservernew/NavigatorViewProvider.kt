package ru.marina.githubrepositoriesobservernew

import androidx.fragment.app.Fragment

interface NavigatorViewProvider {

//    fun navigationHostFragmentToRepositoryInfoFragment(name: String, owner: String): Fragment

    fun navigationHostFragmentToAuthUserFragment()
    fun navigationHostFragmentToRepositoriesListFragment()

}