package com.github.aliftrd.sutori.ui.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.github.aliftrd.sutori.data.story.dto.StoryItem
import com.github.aliftrd.sutori.databinding.LayoutStoryItemBinding
import com.shashank.sony.fancytoastlib.FancyToast

class StoryAdapter: ListAdapter<StoryItem, StoryAdapter.ListViewHolder>(DIFF_CALLBACK) {
    class ListViewHolder(private val binding: LayoutStoryItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StoryItem) {
            with(binding) {
                ivItemPhoto.load(item.photoUrl)
                tvItemName.text = item.name
                tvDescription.text = item.description

                root.setOnClickListener {
                    val navigation = StoryFragmentDirections.actionHomeFragmentToDetailStoryFragment(item)
                    root.findNavController().navigate(navigation)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: LayoutStoryItemBinding = LayoutStoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryItem>() {
            override fun areItemsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: StoryItem, newItem: StoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}