package com.example.githubtask.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.githubtask.R
import com.example.githubtask.adapter.UserAdapter
import com.example.githubtask.adapter.UserLoadStateAdapter
import com.example.githubtask.data.model.UserModel
import com.example.githubtask.databinding.FragmentMainBinding
import com.example.githubtask.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment :
    Fragment(R.layout.fragment_main), UserAdapter.OnItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var userAdapter: UserAdapter

    private val viewModel: UserViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        initRecyclerViewUser()

        binding.btnRetry.setOnClickListener {
            userAdapter.retry()
        }

        viewModel.usersResponse.observe(viewLifecycleOwner, {
            userAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        manageUserStates()
    }


    private fun manageUserStates() {
        userAdapter.addLoadStateListener { loadState ->

            manageViews(loadState)

            if (loadState.source.refresh is LoadState.NotLoading &&
                loadState.append.endOfPaginationReached && userAdapter.itemCount < 1
            ) {
                binding.textViewNoResult.isVisible = true
                binding.recyclerViewUser.isVisible = false
            } else {
                binding.textViewNoResult.isVisible = false
            }

        }
    }

    private fun manageViews(loadState: CombinedLoadStates) {
        binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
        binding.recyclerViewUser.isVisible = loadState.source.refresh is LoadState.NotLoading
        binding.textViewError.isVisible = loadState.source.refresh is LoadState.Error
        binding.btnRetry.isVisible = loadState.source.refresh is LoadState.Error
    }

    private fun initRecyclerViewUser() {
        userAdapter = UserAdapter(this)
        binding.recyclerViewUser.apply {
            adapter = userAdapter.withLoadStateFooter(
                footer = UserLoadStateAdapter { userAdapter.retry() },
            )
            setHasFixedSize(true)
        }
    }

    override fun onItemClick(userModel: UserModel) {
        val action = MainFragmentDirections.actionMainFragmentToUserDetailFragment(userModel)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}