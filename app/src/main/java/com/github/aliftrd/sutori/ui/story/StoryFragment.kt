package com.github.aliftrd.sutori.ui.story

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.aliftrd.sutori.base.BaseFragment
import com.github.aliftrd.sutori.databinding.FragmentStoryBinding
import org.koin.android.ext.android.inject

class StoryFragment : BaseFragment<FragmentStoryBinding>() {
    private val storyViewModel: StoryViewModel by inject()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentStoryBinding = FragmentStoryBinding.inflate(inflater, container, false)

    override fun initIntent() = Unit

    override fun initUI() = Unit

    override fun initAction() {
        binding.addStory.setOnClickListener {
            val action = StoryFragmentDirections.actionHomeFragmentToAddStoryFragment()
            findNavController().navigate(action)
        }
    }

    override fun initProcess() {
        storyViewModel.getStory()
    }

    override fun initObserver() {
        storyViewModel.story.observe(viewLifecycleOwner) {
            val storyAdapter = StoryAdapter()
            binding.rvStory.apply {
                adapter = storyAdapter.withLoadStateFooter(
                    footer = LoadingStateAdapter { storyAdapter.retry() }
                )
                layoutManager = LinearLayoutManager(context)
            }
            storyAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }
}