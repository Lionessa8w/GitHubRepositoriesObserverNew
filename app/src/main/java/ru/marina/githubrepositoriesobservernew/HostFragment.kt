package ru.marina.githubrepositoriesobservernew

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import javax.inject.Inject

@AndroidEntryPoint
class HostFragment : Fragment(), NavigatorFragment {

    @Inject
    lateinit var keyValueStorageApi: KeyValueStorageApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isTokenInit = try {
            keyValueStorageApi.getToken().isNotBlank()
        } catch (e: IllegalStateException) {
            false
        }

        if (!isTokenInit) {
            navigationHostFragmentToAuthUserFragment()
        } else {
            navigationHostFragmentToRepositoriesListFragment()
        }
    }


    override fun navigationHostFragmentToRepositoryInfoFragment(name: String, owner: String) {
        findNavController().navigate(HostFragmentDirections.actionHostFragmentToRepositoryInfoFragment(
            name = name,
            owner = owner
        ))
    }

    override fun navigationHostFragmentToAuthUserFragment() {
        findNavController().navigate(HostFragmentDirections.actionGoToAuth())
    }

    override fun navigationHostFragmentToRepositoriesListFragment() {
        findNavController().navigate(HostFragmentDirections.actionGoToRepositoriesListFragment())
    }
}