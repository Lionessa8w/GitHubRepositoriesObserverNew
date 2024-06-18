package ru.marina.githubrepositoriesobservernew

interface NavigatorActivity {

    fun navigationRepositoriesListFragmentToRepositoryInfoFragment(name: String, owner: String)

    fun navigationHostFragmentToAuthUserFragment()
    fun navigationHostFragmentToRepositoriesListFragment()
    fun navigationAuthFragmentToRepositoriesListFragment()
    fun navigationRepositoriesListFragmentToAuthUserFragment()

}