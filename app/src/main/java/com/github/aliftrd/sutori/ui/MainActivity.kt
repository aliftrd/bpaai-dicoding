package com.github.aliftrd.sutori.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.github.aliftrd.sutori.R
import com.github.aliftrd.sutori.base.BaseActivity
import com.github.aliftrd.sutori.databinding.ActivityMainBinding
import com.github.aliftrd.sutori.utils.ext.gone
import com.github.aliftrd.sutori.utils.ext.show
import java.util.Locale

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun initIntent() {
        //
    }

    override fun initUI() {
        val navHostBottomBar = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navControllerBottomBar = navHostBottomBar.navController

        binding.bottomNavigationView.setupWithNavController(navControllerBottomBar)
        navControllerBottomBar.addOnDestinationChangedListener {_, destination,_ ->
            val isHomeFragment = destination.id == R.id.home_fragment
            val isSettingFragment = destination.id == R.id.setting_fragment

            if (isHomeFragment || isSettingFragment) {
                binding.bottomNavigationView.show()
            } else {
                binding.bottomNavigationView.gone()
            }
        }
    }

    override fun initAction() {
        //
    }

    override fun initProcess() {
        //
    }

    override fun initObserver() {
        //
    }
}