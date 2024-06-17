package ru.marina.githubrepositoriesobservernew

interface NavigatorFragment {

    fun navigationHostFragmentToRepositoryInfoFragment(name: String, owner: String)

    fun navigationHostFragmentToAuthUserFragment()
    fun navigationHostFragmentToRepositoriesListFragment()

}