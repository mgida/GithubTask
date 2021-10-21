package com.example.githubtask.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.githubtask.R
import com.example.githubtask.adapter.FollowerAdapter
import com.example.githubtask.adapter.FollowingAdapter
import com.example.githubtask.databinding.FragmentUserDetailBinding
import com.example.githubtask.utils.Constant.Companion.TAG
import com.example.githubtask.utils.DataState
import com.example.githubtask.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<UserDetailFragmentArgs>()

    private lateinit var followerAdapter: FollowerAdapter
    private lateinit var followingAdapter: FollowingAdapter

    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserDetailBinding.bind(view)

        initRecyclerViewFollowers()
        initRecyclerViewFollowing()

        val user = args.user
        val userName = getString(R.string.user_name_label) + " " + user.name
        val followersUrl = getString(R.string.followers_url_label) + " " + user.followers_url
        val followingUrl = getString(R.string.following_url_label) + " " + user.following_url

        binding.apply {
            Glide.with(binding.root)
                .load(user.avatar_url)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(imageViewDetail)

            tvUserName.text = userName
            tvUserFollowersUrl.text = followersUrl
            tvUserFollowingUrl.text = followingUrl

        }

        viewModel.getUserFollowers(userName = user.name)
        viewModel.getUserFollowing(userName = user.name)

        viewModel.userFollowers.observe(viewLifecycleOwner, { dataState ->

            when (dataState) {
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is DataState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val followers = dataState.data
                    followerAdapter.differ.submitList(followers)
                }
                is DataState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    //   binding.recyclerViewFollowers.visibility = View.GONE
                    Log.d(TAG, "error occurred ${dataState.exception.message}")
                }
            }
        })

        viewModel.userFollowing.observe(viewLifecycleOwner, { dataState ->

            when (dataState) {
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is DataState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val following = dataState.data
                    followingAdapter.differ.submitList(following)
                }
                is DataState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    //    binding.recyclerViewFollowing.visibility = View.GONE
                    Log.d(TAG, "error occurred ${dataState.exception.message}")
                }
            }
        })


    }

    private fun initRecyclerViewFollowers() {

        followerAdapter = FollowerAdapter()
        binding.recyclerViewFollowers.apply {
            adapter = followerAdapter
            setHasFixedSize(true)
        }
    }

    private fun initRecyclerViewFollowing() {

        followingAdapter = FollowingAdapter()
        binding.recyclerViewFollowing.apply {
            adapter = followingAdapter
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}