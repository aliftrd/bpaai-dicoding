package com.github.aliftrd.sutori.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.aliftrd.sutori.base.BaseFragment
import com.github.aliftrd.sutori.databinding.FragmentSettingBinding
import com.github.aliftrd.sutori.di.feature.authModule
import com.github.aliftrd.sutori.di.preferenceModule
import com.github.aliftrd.sutori.ui.login.LoginActivity
import com.github.aliftrd.sutori.utils.PreferenceManager
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    private val prefs: PreferenceManager by inject()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentSettingBinding = FragmentSettingBinding.inflate(inflater, container, false)

    override fun initIntent() {
        //
    }

    override fun initUI() {
        //
    }

    override fun initAction() {
        with(binding) {
            actionChangeLanguage.setOnClickListener {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            actionLogout.setOnClickListener {
                prefs.clearAllPreferences()

                reloadModule()

                startActivity(Intent(requireContext(), LoginActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    private fun reloadModule() {
        unloadKoinModules(preferenceModule)
        loadKoinModules(preferenceModule)
        unloadKoinModules(authModule)
        loadKoinModules(authModule)
    }

    override fun initProcess() {
        //
    }

    override fun initObserver() {
        //
    }
}
