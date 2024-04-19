package com.github.aliftrd.sutori.ui.story.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.map
import com.github.aliftrd.sutori.R
import com.github.aliftrd.sutori.base.BaseFragment
import com.github.aliftrd.sutori.data.lib.ApiResponse
import com.github.aliftrd.sutori.databinding.FragmentStoryMapsBinding
import com.github.aliftrd.sutori.views.CustomLoadingDialog
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.android.ext.android.inject

class StoryMapsFragment : BaseFragment<FragmentStoryMapsBinding>() {
    private val storyMapViewModel: StoryMapViewModel by inject()
    private val loadingDialog: CustomLoadingDialog by lazy { CustomLoadingDialog(requireActivity()) }

    override fun getViewBinding(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): FragmentStoryMapsBinding = FragmentStoryMapsBinding.inflate(inflater, container, false)

    override fun initIntent() = Unit

    override fun initUI() = Unit

    override fun initAction() = Unit

    override fun initProcess() {
        storyMapViewModel.getStory()
    }

    override fun initObserver() {
        val mapFragment =
            childFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment
        storyMapViewModel.story.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ApiResponse.Loading -> loadingDialog.startLoadingDialog()
                is ApiResponse.Success -> {
                    mapFragment.getMapAsync { googleMap ->
                        googleMap.uiSettings.isZoomControlsEnabled = true
                        googleMap.uiSettings.isIndoorLevelPickerEnabled = true
                        googleMap.uiSettings.isCompassEnabled = true
                        googleMap.uiSettings.isMapToolbarEnabled = true

                        state.data.listStory.forEach {
                            val latLng = LatLng(it.lat.toDouble(), it.lon.toDouble())
                            googleMap.addMarker(MarkerOptions().position(latLng).title(it.name))
                        }
                    }
                    loadingDialog.dismissDialog()
                }

                is ApiResponse.Error -> {
                    loadingDialog.dismissDialog()
                    FancyToast.makeText(
                        requireContext(),
                        state.errorMessage,
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                }

                else -> {
                    loadingDialog.dismissDialog()
                    FancyToast.makeText(
                        requireContext(),
                        getString(R.string.unknown_error),
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,
                        false
                    ).show()
                }
            }
        }
    }
}