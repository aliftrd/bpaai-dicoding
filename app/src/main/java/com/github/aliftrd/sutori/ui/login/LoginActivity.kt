package com.github.aliftrd.sutori.ui.login

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.github.aliftrd.sutori.R
import com.github.aliftrd.sutori.base.BaseActivity
import com.github.aliftrd.sutori.data.lib.ApiResponse
import com.github.aliftrd.sutori.databinding.ActivityLoginBinding
import com.github.aliftrd.sutori.ui.MainActivity
import com.github.aliftrd.sutori.ui.register.RegisterActivity
import com.github.aliftrd.sutori.utils.PreferenceManager
import com.github.aliftrd.sutori.utils.Validation
import com.github.aliftrd.sutori.utils.ext.disabled
import com.github.aliftrd.sutori.utils.ext.enabled
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.android.ext.android.inject

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private val loginViewModel: LoginViewModel by inject()

    override fun getViewBinding(): ActivityLoginBinding =
        ActivityLoginBinding.inflate(layoutInflater)

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
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
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
                        this,
                        getString(R.string.login_success),
                        FancyToast.LENGTH_SHORT,
                        FancyToast.SUCCESS,
                        false
                    ).show()
                    Intent(this, MainActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                }

                is ApiResponse.Error -> {
                    binding.btnLogin.resetState()
                    with(binding.alert) {
                        title.text = getString(R.string.error)
                        message.text = state.errorMessage
                        root.visibility = View.VISIBLE
                    }
                }

                else -> {
                    binding.btnLogin.resetState()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        with(binding) {
            edLoginEmail.text?.clear()
            edLoginPassword.text?.clear()
        }
    }
}