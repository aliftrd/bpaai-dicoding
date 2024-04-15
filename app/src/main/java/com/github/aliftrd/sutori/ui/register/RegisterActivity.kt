package com.github.aliftrd.sutori.ui.register

import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.github.aliftrd.sutori.R
import com.github.aliftrd.sutori.base.BaseActivity
import com.github.aliftrd.sutori.data.lib.ApiResponse
import com.github.aliftrd.sutori.databinding.ActivityRegisterBinding
import com.github.aliftrd.sutori.utils.Validation
import com.github.aliftrd.sutori.utils.ext.disabled
import com.github.aliftrd.sutori.utils.ext.enabled
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.android.ext.android.inject

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    private val registerViewModel:RegisterViewModel by inject()
    override fun getViewBinding(): ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)

    override fun initIntent() = Unit
    override fun initUI() {
        with(binding) {
            edRegisterName.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: android.text.Editable?) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setRegisterButtonState()
                }
            })

            edRegisterEmail.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: android.text.Editable?) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setRegisterButtonState()
                }
            })

            edRegisterPassword.addTextChangedListener(object: TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: android.text.Editable?) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setRegisterButtonState()
                }
            })
        }
    }

    private fun setRegisterButtonState() {
        with(binding) {
            with(binding) {
                val name = edRegisterName.text.toString().trim()
                val email = edRegisterEmail.text.toString().trim()
                val password = edRegisterPassword.text.toString().trim()

                val isNameValid = name.isNotEmpty()
                val isEmailValid = email.isNotEmpty() && !Validation.isInvalidEmail(email)
                val isPasswordValid = password.isNotEmpty() && !Validation.isInvalidPassword(password)

                if(isNameValid && isEmailValid && isPasswordValid) btnRegister.enabled() else btnRegister.disabled()
            }
        }
    }

    override fun initAction() {
        with(binding) {
            btnRegister.setOnClickListener {
                val name = edRegisterName.text.toString().trim()
                val email = edRegisterEmail.text.toString().trim()
                val password = edRegisterPassword.text.toString().trim()
                registerViewModel.register(name, email, password)
            }
            signinWrapper.setOnClickListener { finish() }
        }
    }

    override fun initProcess() = Unit

    override fun initObserver() {
        registerViewModel.registerResult.observe(this) { state ->
            when (state) {
                is ApiResponse.Loading -> binding.btnRegister.setLoading()
                is ApiResponse.Success -> {
                    FancyToast.makeText(this, getString(R.string.register_success), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                    finish()
                }
                is ApiResponse.Error -> {
                    binding.btnRegister.resetState()
                    with(binding.alert) {
                        title.text = getString(R.string.error)
                        message.text = state.errorMessage
                        root.visibility = View.VISIBLE
                    }
                }
                else -> binding.btnRegister.resetState()
            }
        }
    }
}