package ru.marina.githubrepositoriesobservernew

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import ru.marina.githubrepositoriesobservernew.fragment.AuthUserFragment
import ru.marina.githubrepositoriesobservernew.fragment.RepositoriesListFragment
import ru.marina.githubrepositoriesobservernew.fragment.RepositoryInfoFragment
import java.lang.IllegalStateException

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigatorViewProvider {

    @Inject
    lateinit var databaseSaveToken: KeyValueStorageSetting

    @Inject
    lateinit var keyValueStorageApi: KeyValueStorageApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databaseSaveToken.bindDB(applicationContext)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val tokenIsInit = try {
                keyValueStorageApi.getToken().isNotBlank()
            } catch (e: IllegalStateException) {
                false
            }
            val startFragment = if (tokenIsInit) {
                RepositoriesListFragment()
            } else {
                AuthUserFragment()
            }
            supportFragmentManager
                .beginTransaction()
                .replace(getViewId(), startFragment)
                .commit()
        }
    }

    override fun onDestroy() {
        databaseSaveToken.releaseDB()
        super.onDestroy()
    }

    override fun getViewId(): Int = R.id.main_container

    override fun getRepositoryInfoFragment(name: String, owner: String): Fragment {
        return RepositoryInfoFragment.newInstance(name, owner)
    }

    override fun getAuthUserFragment(): Fragment {
        return AuthUserFragment()
    }

    override fun getRepositoriesListFragment(): Fragment {
        return RepositoriesListFragment()
    }
}