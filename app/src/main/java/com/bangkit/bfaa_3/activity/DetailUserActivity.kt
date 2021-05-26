package com.bangkit.bfaa_3.activity

import android.content.ContentValues
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.bfaa_3.MainViewModel
import com.bangkit.bfaa_3.R
import com.bangkit.bfaa_3.adapter.SectionsPagerAdapter
import com.bangkit.bfaa_3.database.DbContract
import com.bangkit.bfaa_3.database.UserHelper
import com.bangkit.bfaa_3.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.coroutines.InternalCoroutinesApi

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var mainViewModel: MainViewModel
    private var isFavourite = false
    private lateinit var gitUserHelper: UserHelper

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val usrname = intent.getStringExtra(MainActivity.EXTRA_USER) as String
        val avatar = intent.getStringExtra(MainActivity.EXTRA_AVATAR) as String
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java)

        mainViewModel.setGitUserDetail(usrname)
        mainViewModel.getUserDetail().observe(this, { userItems ->
            if (userItems != null) {
                binding.tvDetname.text = userItems.name
                binding.tvDetusername.text = userItems.username
                Picasso.get().load(userItems.avatar).into(binding.imgItemDetailAvatar)
            }
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = usrname
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tabs, viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        gitUserHelper = UserHelper.getInstance(applicationContext)
        gitUserHelper.open()

        setStatusFavorite(isFavourite)
        binding.fabFavorite.setOnClickListener{
            if (isFavourite) {
                isFavourite = false
                gitUserHelper.deleteById(usrname)
                setStatusFavorite(isFavourite)
            } else {
                isFavourite = true
                val values = ContentValues().apply {
                    put(DbContract.GitUserColumns.USERNAME, usrname)
                    put(DbContract.GitUserColumns.NAME, binding.tvDetname.text.toString())
                    put(DbContract.GitUserColumns.AVATAR_URL, avatar)
                    put(DbContract.GitUserColumns.FAVORITE, isFavourite.toString())
                }
                gitUserHelper.insert(values)
                setStatusFavorite(isFavourite)
            }
        }
        supportActionBar?.elevation = 0f
    }

    override fun onDestroy() {
        super.onDestroy()
        gitUserHelper.close()
    }

    private fun setStatusFavorite(boolean: Boolean) {
        if (boolean) {
            binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

}