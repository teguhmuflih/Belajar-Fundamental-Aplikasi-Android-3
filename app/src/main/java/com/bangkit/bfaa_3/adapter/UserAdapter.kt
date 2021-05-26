package com.bangkit.bfaa_3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.bfaa_3.R
import com.bangkit.bfaa_3.databinding.ItemRowUserBinding
import com.bangkit.bfaa_3.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class UserAdapter : RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private val data = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallBack(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

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
            itemView.setOnClickListener{onItemClickCallback?.onItemClicked(gituser)}
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}