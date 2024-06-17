package ru.marina.githubrepositoriesobservernew

interface NavigatorActivity {

    fun navigationHostFragmentToRepositoryInfoFragment(name: String, owner: String)

    fun navigationHostFragmentToAuthUserFragment()
    fun navigationHostFragmentToRepositoriesListFragment()

}