package ru.marina.githubrepositoriesobservernew

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import ru.marina.githubrepositoriesobservernew.fragment.AuthUserFragment
import ru.marina.githubrepositoriesobservernew.fragment.RepositoryInfoFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigatorViewProvider {

    @Inject
    lateinit var databaseSaveToken: KeyValueStorageSetting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        Log.d("checkResult", "onCreate: ${keyValueStorageSetting}")
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_container, AuthUserFragment())
                .commit()
        }
        //создание бд
        databaseSaveToken.bindDB(applicationContext)

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
        return RepositoryInfoFragment()
    }


}