package ru.marina.githubrepositoriesobservernew

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalStateException
import javax.inject.Inject

@AndroidEntryPoint
class HostFragment : Fragment() {

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
            getNavigatorFragment()?.navigationHostFragmentToAuthUserFragment()
        } else {
            getNavigatorFragment()?.navigationHostFragmentToRepositoriesListFragment()
        }
    }
}