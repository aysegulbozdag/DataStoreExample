package com.example.datastoreexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.datastoreexample.DataStoreManager.Companion.NAME_KEY
import com.example.datastoreexample.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dataStoreManager by lazy {
        DataStoreManager(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPrint.setOnClickListener {
            if (binding.someThingText.text.isNotEmpty()){
                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                        dataStoreManager.myDatastoreWrite(NAME_KEY, binding.someThingText.text.toString())
                    }
                }

            }
        }

        binding.btnFetch.setOnClickListener {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    dataStoreManager.myDatastoreRead(NAME_KEY).collect {
                        Log.i("Gelen text", it)
                    }
                }
            }

        }
    }
}