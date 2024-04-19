package com.github.aliftrd.sutori.ui.setting

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.github.aliftrd.sutori.base.BaseFragment
import com.github.aliftrd.sutori.databinding.FragmentSettingBinding
import com.github.aliftrd.sutori.R
import org.koin.android.ext.android.inject

class SettingFragment : BaseFragment<FragmentSettingBinding>() {
    private val settingViewModel: SettingViewModel by inject()

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
                settingViewModel.logout()
                findNavController().navigate(R.id.action_setting_fragment_to_login_fragment)
            }
        }
    }

    override fun initProcess() {
        //
    }

    override fun initObserver() {
        //
    }
}
