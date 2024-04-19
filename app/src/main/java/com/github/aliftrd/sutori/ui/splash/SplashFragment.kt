package com.github.aliftrd.sutori.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.aliftrd.sutori.R
import com.github.aliftrd.sutori.base.BaseFragment
import com.github.aliftrd.sutori.databinding.FragmentSplashBinding
import com.github.aliftrd.sutori.utils.ConstVar
import com.github.aliftrd.sutori.utils.PreferenceManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val pref: PreferenceManager by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSplashBinding = FragmentSplashBinding.inflate(inflater, container, false)

    override fun initIntent() = Unit

    override fun initUI() = Unit

    override fun initAction() = Unit

    override fun initProcess() {
        val isLogin = pref.getToken

        viewLifecycleOwner.lifecycleScope.launch {
            delay(ConstVar.SPLASH_DELAY_TIME)
            when (isLogin.isNotEmpty()) {
                true -> findNavController().navigate(R.id.action_splash_fragment_to_home_fragment)
                else -> findNavController().navigate(R.id.action_splash_fragment_to_login_fragment)
            }
        }
    }

    override fun initObserver() = Unit

}