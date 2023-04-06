package com.bangkit.githubuserapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.bangkit.githubuserapp.Data.model.UserGithub
import com.bangkit.githubuserapp.databinding.UserItemBinding

class UserAdapter(
    private val data: MutableList<UserGithub.Item> = mutableListOf(),
    private val listener: (UserGithub.Item) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(private val v: UserItemBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(item: UserGithub.Item) {
            v.imgAvatar.load(item.avatar_url) {
                transformations(CircleCropTransformation())
            }

            v.txtUsername.text = item.login
        }
    }

    fun setData(data: List<UserGithub.Item>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

}