package com.example.githubtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubtask.R
import com.example.githubtask.data.model.UserModel
import com.example.githubtask.databinding.UserListItemBinding


class UserAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<UserModel, UserAdapter.UserViewHolder>(DIFF_CALLBACK) {

    interface OnItemClickListener {
        fun onItemClick(userModel: UserModel)
    }

    inner class UserViewHolder(private val binding: UserListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentUser = getItem(position)
                    currentUser?.let {
                        listener.onItemClick(it)
                    }
                }
            }
        }

        fun bind(user: UserModel?) {
            binding.apply {
                Glide.with(itemView)
                    .load(user?.avatar_url)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(imageViewUser)

                tvUserLogin.text = user?.name
                tvUserRepo.text = user?.repos_url
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel) =
                oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            UserListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser: UserModel? = getItem(position)
        holder.bind(currentUser)
    }
}






