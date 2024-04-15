package com.github.aliftrd.sutori.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.github.aliftrd.sutori.data.auth.AuthRepository
import com.github.aliftrd.sutori.databinding.ActivitySplashBinding
import com.github.aliftrd.sutori.ui.MainActivity
import com.github.aliftrd.sutori.ui.login.LoginActivity
import com.github.aliftrd.sutori.utils.ConstVar
import com.github.aliftrd.sutori.utils.PreferenceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import kotlin.time.Duration.Companion.seconds

class SplashActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    private val prefs: PreferenceManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if(prefs.getToken.isEmpty()) {
            moveActivity(LoginActivity::class.java)
        } else {
            moveActivity(MainActivity::class.java)
        }
    }

    private fun moveActivity(destination: Class<*>) {
        lifecycleScope.launch {
            delay(ConstVar.SPLASH_DELAY_TIME)
            startActivity(Intent(this@SplashActivity, destination))
            finish()
        }
    }
}