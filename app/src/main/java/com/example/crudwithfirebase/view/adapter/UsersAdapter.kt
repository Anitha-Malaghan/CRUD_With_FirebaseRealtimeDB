package com.example.crudwithfirebase.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudwithfirebase.model.entity.Users
import com.example.crudwithfirebase.databinding.UsersItemBinding
import com.example.crudwithfirebase.view.ui.UpdateUserActivity

class UsersAdapter(
    var context: Context,
    var userList: List<Users>
    ): RecyclerView.Adapter<UsersAdapter.UserViewHolder>(){

    inner class UserViewHolder(val adapterBinding: UsersItemBinding)
        : RecyclerView.ViewHolder(adapterBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UsersItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        with(holder.adapterBinding) {
            tvName.text = user.userName
            tvAge.text = user.userAge.toString()
            tvEmail.text = user.userEmail

            idLinearLayout.setOnClickListener {

                val intent = Intent(context, UpdateUserActivity::class.java)
                intent.putExtra("id", userList[position].userId)
                intent.putExtra("name", userList[position].userName)
                intent.putExtra("age", userList[position].userAge)
                intent.putExtra("email", userList[position].userEmail)
                context.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    fun getUserId(position: Int): String{
        return userList[position].userId
    }

    fun updateList(newList: List<Users>) {
        userList = newList
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }
}