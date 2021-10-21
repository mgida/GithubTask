package com.example.githubtask.ui

import com.example.githubtask.adapter.UserAdapter
import com.example.githubtask.adapter.UserLoadStateAdapter
import com.example.githubtask.viewmodel.UserViewModel
import com.example.githubtask.viewmodel.UserViewModelFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.githubtask.databinding.ActivityMainBinding
import com.example.githubtask.repository.UserRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val userRepository = UserRepository()
        val viewModelFactory = UserViewModelFactory(userRepository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]

        initRecyclerViewUser()

        binding.btnRetry.setOnClickListener {
            userAdapter.retry()
        }

        viewModel.usersResponse.observe(this, {
            userAdapter.submitData(lifecycle, it)
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
        userAdapter = UserAdapter()
        binding.recyclerViewUser.apply {
            adapter = userAdapter.withLoadStateHeaderAndFooter(
                footer = UserLoadStateAdapter { userAdapter.retry() },
                header = UserLoadStateAdapter { userAdapter.retry() },
            )
            setHasFixedSize(true)
        }
    }
}