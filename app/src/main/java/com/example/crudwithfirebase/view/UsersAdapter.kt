package com.example.crudwithfirebase.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudwithfirebase.databinding.ActivityAddUserBinding
import com.example.crudwithfirebase.databinding.UsersItemBinding

class UsersAdapter(var context: Context,
                    var userList: ArrayList<Users>): RecyclerView.Adapter<UsersAdapter.UserViewHolder>(){

    inner class UserViewHolder(val adapterBinding: UsersItemBinding)
        : RecyclerView.ViewHolder(adapterBinding.root){}

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

        }
        holder.adapterBinding.idLinearLayout.setOnClickListener {

            val intent = Intent(context,UpdateUserActivity::class.java)
            intent.putExtra("id", userList[position].userId)
            intent.putExtra("name", userList[position].userName)
            intent.putExtra("age", userList[position].userAge)
            intent.putExtra("email", userList[position].userEmail)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    fun getUserId(position: Int): String{
        return userList[position].userId
    }
}