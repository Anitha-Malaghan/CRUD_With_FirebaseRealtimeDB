package com.example.crudwithfirebase.model.remote

import com.example.crudwithfirebase.model.entity.Users
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseDataSource {
    //Creating the instance for the firebase database class for the realtime data
    private val myReference =
        FirebaseDatabase.getInstance().getReference("MyUsers")

    fun getUsers(listener: ValueEventListener) {
        myReference.addValueEventListener(listener)
    }

    fun addUser(user: Users, callback: (Boolean, String?) -> Unit) {

        val id = myReference.push().key ?: return
        val newUser = user.copy(userId = id)

        myReference.child(id).setValue(newUser)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    val errorMessage =
                        task.exception?.localizedMessage
                            ?: task.exception.toString()

                    callback(false, errorMessage)
                }
            }
    }

    fun updateUser(
        userId: String,
        userMap: Map<String, Any>,
        callback: (Boolean, String?) -> Unit
    ) {

        myReference.child(userId)
            .updateChildren(userMap)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false,
                        task.exception?.localizedMessage
                            ?: task.exception.toString())
                }
            }
    }


    fun deleteUser(
        userId: String,
        callback: (Boolean, String?) -> Unit
    ) {

        myReference.child(userId)
            .removeValue()
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(
                        false,
                        task.exception?.localizedMessage
                            ?: task.exception.toString()
                    )
                }
            }
    }

}
