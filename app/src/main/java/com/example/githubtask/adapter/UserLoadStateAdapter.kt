package com.example.githubtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubtask.databinding.UserLoadStateAdapterFooterBinding

class UserLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UserLoadStateAdapter.LoadStateViewHolder>() {

    inner class LoadStateViewHolder(private val binding: UserLoadStateAdapterFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetryFooter.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBarFooter.isVisible = loadState is LoadState.Loading
                textViewErrorFooter.isVisible = loadState !is LoadState.Loading
                btnRetryFooter.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            UserLoadStateAdapterFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}