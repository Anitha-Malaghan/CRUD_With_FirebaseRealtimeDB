package com.example.crudwithfirebase.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crudwithfirebase.databinding.ActivityAddUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddUserActivity : AppCompatActivity() {
    lateinit var addUserBinding: ActivityAddUserBinding

    //Creating the instance for the firebase database class for the realtime data
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    //Create an object from the database object
    val myReference: DatabaseReference = database.reference.child("MyUsers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addUserBinding = ActivityAddUserBinding.inflate(layoutInflater)
        val view = addUserBinding.root
        setContentView(view)

        supportActionBar?.title = "Add User"

        addUserBinding.btAddUser.setOnClickListener {
            Log.d("ADD_USER", "Button clicked")
            addUserToDatabase()
            Log.d("ADD_USER", "Button action completed")
        }

    }
    fun addUserToDatabase(){
        val name: String = addUserBinding.etName.text.toString()
        val age: Int = addUserBinding.etAge.text.toString().toInt()
        val email: String = addUserBinding.etMail.text.toString()

        //push() creates unique key for each user under MyUser's child
        val id: String = myReference.push().key.toString()

        //Create the object from the Users data class
        val user = Users(id, name, age, email)

        //inserting in to the firebase database with respect to their id and confirming
        myReference.child(id).setValue(user).addOnCompleteListener { task->

            if(task.isSuccessful){
                Toast.makeText(
                    this,
                    "User has be added to the database successfully",
                    Toast.LENGTH_SHORT).show()

                finish()
            }else{
                Toast.makeText(
                    this,
                    task.exception.toString(),
                    Toast.LENGTH_SHORT).show()

            }

        }
    }
}