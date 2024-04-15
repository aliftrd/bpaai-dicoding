package com.github.aliftrd.sutori.ui.story.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import coil.load
import com.github.aliftrd.sutori.R
import com.github.aliftrd.sutori.base.BaseFragment
import com.github.aliftrd.sutori.data.story.dto.StoryItem
import com.github.aliftrd.sutori.databinding.FragmentDetailStoryBinding

class DetailStoryFragment: BaseFragment<FragmentDetailStoryBinding>() {
    private var storyItem: StoryItem? = null
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentDetailStoryBinding = FragmentDetailStoryBinding.inflate(inflater, container, false)

    override fun initIntent() {
        storyItem = DetailStoryFragmentArgs.fromBundle(requireArguments()).story
    }

    override fun initUI() {
        with(binding) {
            tvDetailPhoto.load(storyItem?.photoUrl)
            tvDetailName.text = storyItem?.name
            tvDetailDescription.text = storyItem?.description
        }
    }

    override fun initAction() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
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