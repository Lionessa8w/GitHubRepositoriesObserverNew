package ru.marina.githubrepositoriesobservernew

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import javax.inject.Inject

@AndroidEntryPoint
class HostFragment : Fragment(), NavigatorViewProvider {

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

//    override fun navigationHostFragmentToRepositoryInfoFragment(name: String, owner: String): Fragment {
//        findNavController().navigate(HostFragmentDirections.)
//    }

    override fun navigationHostFragmentToAuthUserFragment() {
        findNavController().navigate(HostFragmentDirections.actionGoToRepositoriesListFragment())
    }

    override fun navigationHostFragmentToRepositoriesListFragment() {
        findNavController().navigate(HostFragmentDirections.actionGoToAuth())
    }
}