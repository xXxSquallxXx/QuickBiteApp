package com.example.quickbiteapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quickbiteapp.databinding.ActivityMainBinding
import com.example.quickbiteapp.di.MainApplication
import com.example.quickbiteapp.ui.navigation.NavigationManager
import com.example.quickbiteapp.util.UiInitializer

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val appComponent = (application as MainApplication).appComponent
        if (savedInstanceState == null) {
            UiInitializer.initUi(this, binding, appComponent)
        } else {
            NavigationManager.setFragmentManager(supportFragmentManager)
            UiInitializer.setupBottomNavigation(this, binding, appComponent)
            UiInitializer.setupBackStackListener(this, binding)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}