package com.bangkit.bfaa_3.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.bfaa_3.MainViewModel
import com.bangkit.bfaa_3.adapter.UserAdapter
import com.bangkit.bfaa_3.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {

    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_USERNAME = "username"

        fun newInstance(username: String): FollowersFragment {
            val fragment = FollowersFragment()
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
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(ARG_USERNAME)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.adapter = adapter
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)
        if (username != null) {
            mainViewModel.setGitUserFollowers(username)
            showLoading(true)
        }
        mainViewModel.getGitUserFollowers().observe(viewLifecycleOwner, { userItems ->
            if (userItems != null && userItems.isNotEmpty()) {
                adapter.setData(userItems)
                showLoading(false)
                binding.rvFollowers.visibility = View.VISIBLE
                binding.searchNotFoundF1.visibility = View.GONE
            } else {
                showLoading(false)
                binding.rvFollowers.visibility = View.INVISIBLE
                binding.searchNotFoundF1.visibility = View.VISIBLE
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBarF1.visibility = View.VISIBLE
        } else {
            binding.progressBarF1.visibility = View.GONE
        }
    }
}