package ru.marina.githubrepositoriesobservernew

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var keyValueStorageSetting: KeyValueStorageSetting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("checkResult", "onCreate: ${keyValueStorageSetting}")
        keyValueStorageSetting.bindDB(this)
    }
}