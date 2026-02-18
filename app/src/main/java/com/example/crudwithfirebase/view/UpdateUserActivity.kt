package com.example.crudwithfirebase.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudwithfirebase.databinding.ActivityUpdateUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateUserActivity : AppCompatActivity() {
    lateinit var updateUserBinding: ActivityUpdateUserBinding

    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val myReference: DatabaseReference = database.reference.child("MyUsers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateUserBinding = ActivityUpdateUserBinding.inflate(layoutInflater)
        val view = updateUserBinding.root
        setContentView(view)

        supportActionBar?.title = "Update User"
        getAndSetData()

        updateUserBinding.btUpdateUser.setOnClickListener {
            updateData()
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

    fun updateData(){
        val updatedName: String =  updateUserBinding.etUpdateName.text.toString()
        val updatedAge: Int =  updateUserBinding.etUpdateAge.text.toString().toInt()
        val updatedEmail: String =  updateUserBinding.etUpdateEmail.text.toString()
        val userId  = intent.getStringExtra("id")

        val userMap = mutableMapOf<String,Any>()
        userMap["userId"] = userId.toString()
        userMap["userName"] = updatedName
        userMap["userAge"] = updatedAge
        userMap["userEmail"] = updatedEmail

        if (userId != null) {
            myReference.child(userId).updateChildren(userMap).addOnCompleteListener {task->
                if(task.isSuccessful){
                    Toast.makeText(
                        this,
                        "User has be added to the database successfully",
                        Toast.LENGTH_SHORT).show()

                    finish()

                }

            }
        }

    }

}