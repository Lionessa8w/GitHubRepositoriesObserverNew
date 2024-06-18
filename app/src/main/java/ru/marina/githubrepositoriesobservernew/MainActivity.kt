package ru.marina.githubrepositoriesobservernew

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import ru.marina.githubrepositoriesobservernew.fragment.AuthUserFragmentDirections
import ru.marina.githubrepositoriesobservernew.fragment.RepositoriesListFragmentDirections

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigatorActivity {

    @Inject
    lateinit var databaseSaveToken: KeyValueStorageSetting

    private val getAnyFragment
        get() = supportFragmentManager.fragments.firstOrNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databaseSaveToken.bindDB(applicationContext)

        setContentView(R.layout.activity_main)

    }

    override fun onDestroy() {
        databaseSaveToken.releaseDB()
        super.onDestroy()
    }

    override fun navigationRepositoriesListFragmentToRepositoryInfoFragment(name: String, owner: String) {
        val fragment = getAnyFragment ?: return
        fragment.findNavController().navigate(
            RepositoriesListFragmentDirections.actionGoToRepositoryInfoFragment(
                name = name,
                owner = owner
            )
        )
    }

    override fun navigationHostFragmentToAuthUserFragment() {
        val fragment = getAnyFragment ?: return
        fragment.findNavController()
            .navigate(
                HostFragmentDirections
                    .actionGoToAuth()
            )
    }


    override fun navigationHostFragmentToRepositoriesListFragment() {
        val fragment = getAnyFragment ?: return
        fragment.findNavController()
            .navigate(
                HostFragmentDirections
                    .actionGoToRepositoriesListFragment()
            )
    }

    override fun navigationAuthFragmentToRepositoriesListFragment() {
        val fragment = getAnyFragment ?: return
        fragment.findNavController()
            .navigate(
                AuthUserFragmentDirections
                    .actionGoToRepositoriesListFragment()
            )
    }

    override fun navigationRepositoriesListFragmentToAuthUserFragment() {
        val fragment = getAnyFragment ?: return
        fragment.findNavController().navigate(
            RepositoriesListFragmentDirections
                .actionRepositoriesListFragmentToAuthUserFragment2()
        )
    }
}