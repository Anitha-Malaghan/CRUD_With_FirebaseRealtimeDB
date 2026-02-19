package com.example.crudwithfirebase.model.repository

import com.example.crudwithfirebase.model.entity.Users
import com.example.crudwithfirebase.model.remote.FirebaseDataSource
import com.google.firebase.database.ValueEventListener

class UsersRepository(
    private val firebaseDataSource: FirebaseDataSource
) {

    fun getUsers(listener: ValueEventListener) {
        firebaseDataSource.getUsers(listener)
    }

    fun addUser(user: Users, callback: (Boolean, String?)->Unit) {
        firebaseDataSource.addUser(user, callback)
    }

    fun updateUser(
        userId: String,
        userMap: Map<String, Any>,
        callback: (Boolean, String?) -> Unit
    ) {
        firebaseDataSource.updateUser(userId, userMap, callback)
    }

    fun deleteUser(
        id: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        firebaseDataSource.deleteUser(id) { success, message ->
            onResult(success, message)
        }
    }
}
