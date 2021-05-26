package com.bangkit.bfaa_3.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bfaa_3.CustomOnItemsClickListener
import com.bangkit.bfaa_3.R
import com.bangkit.bfaa_3.activity.DetailUserActivity
import com.bangkit.bfaa_3.activity.MainActivity
import com.bangkit.bfaa_3.databinding.ItemRowUserBinding
import com.bangkit.bfaa_3.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {
    val data = ArrayList<User>()

    fun setData(items: ArrayList<User>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return ListViewHolder(mView)
    }

    override fun onBindViewHolder(listViewHolder: ListViewHolder, position: Int) {
        listViewHolder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)
        fun bind(gituser: User) {
            binding.tvUsername.text = gituser.username
            Glide.with(itemView.context)
                .load(gituser.avatar)
                .apply(RequestOptions().override(55,55))
                .into(binding.imgItemAvatar)
            itemView.setOnClickListener(CustomOnItemsClickListener(adapterPosition, object : CustomOnItemsClickListener.OnItemClickCallback {
                override fun onItemClicked(view: View, position: Int) {
                    val intent = Intent(activity, DetailUserActivity::class.java)
                    intent.putExtra(MainActivity.EXTRA_USER, gituser.username)
                    intent.putExtra(MainActivity.EXTRA_AVATAR, gituser.avatar)
                    activity.startActivity(intent)
                }
            }))
        }
    }
}