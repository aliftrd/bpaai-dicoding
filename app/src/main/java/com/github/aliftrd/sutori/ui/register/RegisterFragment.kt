package com.github.aliftrd.sutori.ui.register

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.github.aliftrd.sutori.R
import com.github.aliftrd.sutori.base.BaseFragment
import com.github.aliftrd.sutori.data.lib.ApiResponse
import com.github.aliftrd.sutori.databinding.FragmentRegisterBinding
import com.github.aliftrd.sutori.utils.Validation
import com.github.aliftrd.sutori.utils.ext.disabled
import com.github.aliftrd.sutori.utils.ext.enabled
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.android.ext.android.inject

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
    private val registerViewModel:RegisterViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRegisterBinding = FragmentRegisterBinding.inflate(inflater, container, false)

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
            signinWrapper.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    override fun initProcess() = Unit

    override fun initObserver() {
        registerViewModel.registerResult.observe(this) { state ->
            when (state) {
                is ApiResponse.Loading -> binding.btnRegister.setLoading()
                is ApiResponse.Success -> {
                    FancyToast.makeText(requireContext(), getString(R.string.register_success), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                    findNavController().popBackStack()
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