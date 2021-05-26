package com.bangkit.bfaa_3.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bfaa_3.MainViewModel
import com.bangkit.bfaa_3.R
import com.bangkit.bfaa_3.adapter.UserAdapter
import com.bangkit.bfaa_3.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() =  _binding!!

    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.adapter = adapter
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        if (username != null) {
            mainViewModel.setGitUserFollowing(username)
            showLoading(true)
        }
        mainViewModel.getGitUserFollowing().observe(viewLifecycleOwner, { userItems ->
            if (userItems != null && userItems.isNotEmpty()) {
                adapter.setData(userItems)
                showLoading(false)
                binding.rvFollowing.visibility = View.VISIBLE
                binding.searchNotFoundF2.visibility = View.GONE
            } else {
                showLoading(false)
                binding.rvFollowing.visibility = View.INVISIBLE
                binding.searchNotFoundF2.visibility = View.VISIBLE
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarF2.visibility = View.VISIBLE
        } else {
            binding.progressBarF2.visibility = View.GONE
        }
    }
}