package com.github.aliftrd.sutori.ui.story.add

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.github.aliftrd.sutori.R
import com.github.aliftrd.sutori.base.BaseFragment
import com.github.aliftrd.sutori.data.lib.ApiResponse
import com.github.aliftrd.sutori.databinding.FragmentAddStoryBinding
import com.github.aliftrd.sutori.utils.CameraHelper
import com.github.aliftrd.sutori.utils.PreferenceManager
import com.github.aliftrd.sutori.utils.ext.disabled
import com.github.aliftrd.sutori.utils.ext.enabled
import com.github.aliftrd.sutori.views.CustomLoadingDialog
import com.shashank.sony.fancytoastlib.FancyToast
import com.yalantis.ucrop.UCrop
import org.koin.android.ext.android.inject
import java.io.File
import java.util.Date

class AddStoryFragment : BaseFragment<FragmentAddStoryBinding>() {
    private var currentImageUri: Uri? = null
    private val addStoryViewModel: AddStoryViewModel by inject()
    private lateinit var loadingDialog: CustomLoadingDialog

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentAddStoryBinding = FragmentAddStoryBinding.inflate(inflater, container, false)

    override fun initIntent() {
        //
    }

    override fun initUI() {
        loadingDialog = CustomLoadingDialog(requireActivity())
    }

    override fun initAction() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
            btnCamera.setOnClickListener {
                currentImageUri = CameraHelper.getImageUri(requireContext())
                launcherCamera.launch(currentImageUri)
            }
            btnGallery.setOnClickListener {
                launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
            buttonAdd.setOnClickListener {
                val description = binding.edAddDescription.text.toString()

                if(description.isNotEmpty() && currentImageUri != null) {
                    addStoryViewModel.uploadStory(currentImageUri!!, description)
                    buttonAdd.disabled()
                } else {
                    FancyToast.makeText(requireContext(), getString(R.string.please_fill_all_fields), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                }
            }
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if(uri != null) {
            launchUCrop(uri)
        }
    }

    private val launcherCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess->
        if(isSuccess) {
            launchUCrop(currentImageUri!!)
        }
    }

    private fun launchUCrop(uri: Uri) {
        val timestamp = Date().time
        val cachedImage = File(requireActivity().cacheDir, "cropped_image_${timestamp}.jpg")

        val destinationUri = Uri.fromFile(cachedImage)

        val uCrop = UCrop.of(uri, destinationUri).withAspectRatio(1f, 1f)

        uCrop.getIntent(requireContext()).apply {
            launcherUCrop.launch(this)
        }
    }

    private val launcherUCrop =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode ==  Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                if (resultUri != null) {
                    currentImageUri = resultUri
                    setImagePreview()
                }
            }
        }

    private fun setImagePreview() {
        currentImageUri?.let {
            binding.ivStory.setImageURI(it)
        }
    }

    override fun initProcess() {
        //
    }

    override fun initObserver() {
        addStoryViewModel.addStoryResult.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ApiResponse.Loading -> loadingDialog.startLoadingDialog()
                is ApiResponse.Success -> {
                    loadingDialog.dismissDialog()
                    findNavController().popBackStack()
                }
                is ApiResponse.Error -> {
                    loadingDialog.dismissDialog()
                    binding.buttonAdd.enabled()
                    FancyToast.makeText(requireContext(), state.errorMessage, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                }
                else -> {
                    loadingDialog.dismissDialog()
                    binding.buttonAdd.enabled()
                    FancyToast.makeText(requireContext(), getString(R.string.unknown_error), FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
                }
            }
        }
    }
}