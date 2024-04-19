package com.github.aliftrd.sutori.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.github.aliftrd.sutori.R
import com.github.aliftrd.sutori.base.BaseFragment
import com.github.aliftrd.sutori.data.lib.ApiResponse
import com.github.aliftrd.sutori.databinding.FragmentLoginBinding
import com.github.aliftrd.sutori.utils.Validation
import com.github.aliftrd.sutori.utils.ext.disabled
import com.github.aliftrd.sutori.utils.ext.enabled
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val loginViewModel: LoginViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false)

    override fun initIntent() = Unit

    override fun initUI() {
        with(binding) {
            edLoginEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setLoginButtonState()
                }
            })

            edLoginPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setLoginButtonState()
                }
            })
        }
    }

    private fun setLoginButtonState() {
        with(binding) {
            val email = edLoginEmail.text.toString().trim()
            val password = edLoginPassword.text.toString().trim()

            val isEmailValid = email.isNotEmpty() && !Validation.isInvalidEmail(email)
            val isPasswordValid = password.isNotEmpty() && !Validation.isInvalidPassword(password)

            if (isEmailValid && isPasswordValid) btnLogin.enabled() else btnLogin.disabled()
        }
    }

    override fun initAction() {
        with(binding) {
            btnLogin.setOnClickListener {
                val email = edLoginEmail.text.toString().trim()
                val password = edLoginPassword.text.toString().trim()
                loginViewModel.login(email, password)
            }
            registerWrapper.setOnClickListener {
                findNavController().navigate(R.id.action_login_fragment_to_register_fragment)
            }
        }
    }

    override fun initProcess() = Unit

    override fun initObserver() {
        loginViewModel.loginResult.observe(this) { state ->
            when (state) {
                is ApiResponse.Loading -> binding.btnLogin.setLoading()
                is ApiResponse.Success -> {
                    FancyToast.makeText(
                        requireContext(),
                        getString(R.string.login_success),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                    findNavController().navigate(R.id.action_login_fragment_to_home_fragment)
                }
                is ApiResponse.Error -> {
                    binding.btnLogin.resetState()
                    with(binding.alert) {
                        title.text = getString(R.string.error)
                        message.text = state.errorMessage
                        root.visibility = View.VISIBLE
                    }
                }
                else -> binding.btnLogin.resetState()
            }
        }
    }

}