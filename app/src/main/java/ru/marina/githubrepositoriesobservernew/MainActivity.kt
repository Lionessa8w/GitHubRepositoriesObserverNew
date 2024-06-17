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
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var databaseSaveToken: KeyValueStorageSetting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databaseSaveToken.bindDB(applicationContext)

        setContentView(R.layout.activity_main)

    }

    override fun onDestroy() {
        databaseSaveToken.releaseDB()
        super.onDestroy()
    }

}