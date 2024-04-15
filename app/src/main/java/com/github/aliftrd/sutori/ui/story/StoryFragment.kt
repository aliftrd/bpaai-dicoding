package com.github.aliftrd.sutori.ui.story

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.aliftrd.sutori.R
import com.github.aliftrd.sutori.base.BaseFragment
import com.github.aliftrd.sutori.data.lib.ApiResponse
import com.github.aliftrd.sutori.databinding.FragmentStoryBinding
import com.github.aliftrd.sutori.utils.PreferenceManager
import com.github.aliftrd.sutori.utils.ext.gone
import com.github.aliftrd.sutori.utils.ext.show
import com.github.aliftrd.sutori.views.CustomLoadingDialog
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.android.ext.android.inject

class StoryFragment : BaseFragment<FragmentStoryBinding>() {
    private val storyViewModel: StoryViewModel by inject()
    private val loadingDialog: CustomLoadingDialog by lazy { CustomLoadingDialog(requireActivity()) }
     private val prefs: PreferenceManager by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentStoryBinding = FragmentStoryBinding.inflate(inflater, container, false)

    override fun initIntent() {
        //
    }

    override fun initUI() {
        //
    }

    override fun initAction() {
        with(binding) {
            addStory.setOnClickListener {
                val navigateToAddStory = StoryFragmentDirections.actionHomeFragmentToAddStoryFragment()
                findNavController().navigate(navigateToAddStory)
            }
        }
    }

    override fun initProcess() {
        storyViewModel.get()
    }

    override fun initObserver() {
        storyViewModel.story.observe(viewLifecycleOwner) { state ->
            when(state) {
                is ApiResponse.Loading -> loadingDialog.startLoadingDialog()
                is ApiResponse.Success -> {
                    val storyAdapter = StoryAdapter()
                    storyAdapter.submitList(state.data.listStory)
                    binding.rvStory.apply {
                        adapter = storyAdapter
                        layoutManager = LinearLayoutManager(context)
                    }
                    loadingDialog.dismissDialog()
                }
                is ApiResponse.Error -> {
                    loadingDialog.dismissDialog()
                    FancyToast.makeText(requireContext(), state.errorMessage, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show()
                }
                else -> {
                    loadingDialog.dismissDialog()
                    FancyToast.makeText(requireContext(), getString(R.string.unknown_error), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show()
                }
            }
        }
    }
}