package com.example.crudwithfirebase.view.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.crudwithfirebase.model.entity.Users
import com.example.crudwithfirebase.databinding.ActivityAddUserBinding
import com.example.crudwithfirebase.viewmodel.OperationResult
import com.example.crudwithfirebase.viewmodel.UsersViewModel


class AddUserActivity : AppCompatActivity() {

    private lateinit var addUserBinding: ActivityAddUserBinding
    private val viewModel: UsersViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addUserBinding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(addUserBinding.root)

        setSupportActionBar(addUserBinding.toolbar)
        supportActionBar?.title = "Add User"

        addUserBinding.btAddUser.setOnClickListener {

            addUser()

        }
        viewModel.operationStatus.observe(this) { result ->

            when (result) {

                is OperationResult.Success -> {
                    Toast.makeText(
                        this,
                        "User added successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }

                is OperationResult.Error -> {
                    Toast.makeText(
                        this,
                        result.message,   //Actual Firebase exception
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    fun addUser(){
        val name: String = addUserBinding.etName.text.toString()
        val age: Int? = addUserBinding.etAge.text.toString().toIntOrNull()
        val email: String = addUserBinding.etMail.text.toString()

        if (name.isEmpty() || age == null || email.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        //Create the object from the Users data class
        val user = Users("", name, age, email)
        viewModel.addUser(user)

    }

}