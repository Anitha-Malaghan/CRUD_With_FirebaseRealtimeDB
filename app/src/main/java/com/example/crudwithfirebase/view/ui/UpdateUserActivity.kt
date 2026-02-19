package com.example.crudwithfirebase.view.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.crudwithfirebase.databinding.ActivityUpdateUserBinding
import com.example.crudwithfirebase.viewmodel.UsersViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateUserActivity : AppCompatActivity() {

    private lateinit var updateUserBinding: ActivityUpdateUserBinding
    private val viewModel: UsersViewModel by viewModels()
    private lateinit var userId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userId = intent.getStringExtra("id")
            ?: run {
                Toast.makeText(this, "Invalid user", Toast.LENGTH_SHORT).show()
                finish()
                return
            }

        updateUserBinding = ActivityUpdateUserBinding.inflate(layoutInflater)
        setContentView(updateUserBinding.root)

        setSupportActionBar(updateUserBinding.toolbar)
        supportActionBar?.title = "Update User"
        getAndSetData()

        updateUserBinding.btUpdateUser.setOnClickListener {
            updateUser()
        }

    }
    fun getAndSetData(){
        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age", 0).toString()
        val email = intent.getStringExtra("email")

        updateUserBinding.apply {
            etUpdateName.setText(name)
            etUpdateAge.setText(age)
            etUpdateEmail.setText(email)
        }

    }

    fun updateUser(){
        val updatedName: String =  updateUserBinding.etUpdateName.text.toString()
        val updatedAge: Int =  updateUserBinding.etUpdateAge.text.toString().toInt()
        val updatedEmail: String =  updateUserBinding.etUpdateEmail.text.toString()


        if (updatedName.isEmpty() || updatedAge == null || updatedEmail.isEmpty()) {
            Toast.makeText(this, "Make sure none of the fields are empty.", Toast.LENGTH_SHORT).show()
            return
        }

        val userMap = mapOf(
            "userId" to userId,
            "userName" to updatedName,
            "userAge" to updatedAge,
            "userEmail" to updatedEmail
        )
        viewModel.updateUser(userId, userMap)

        /*if (userId != null) {
            myReference.child(userId).updateChildren(userMap).addOnCompleteListener {task->
                if(task.isSuccessful){
                    Toast.makeText(
                        this,
                        "User has be added to the database successfully",
                        Toast.LENGTH_SHORT).show()

                    finish()

                }

            }
        }*/

    }

}