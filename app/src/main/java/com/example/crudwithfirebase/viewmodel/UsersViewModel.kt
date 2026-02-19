package com.example.crudwithfirebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.crudwithfirebase.model.entity.Users
import com.example.crudwithfirebase.model.remote.FirebaseDataSource
import com.example.crudwithfirebase.model.repository.UsersRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class UsersViewModel : ViewModel() {

    private val repository =
        UsersRepository(FirebaseDataSource())

    private val _users = MutableLiveData<List<Users>>()
    val users: LiveData<List<Users>> = _users


    private val _operationStatus = MutableLiveData<OperationResult>()
    val operationStatus: LiveData<OperationResult> = _operationStatus

    fun fetchUsers() {
        repository.getUsers(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Users>()

                for (eachUser in snapshot.children) {
                    val user = eachUser.getValue(Users::class.java)
                    if (user != null) list.add(user)
                }

                _users.value = list
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun addUser(user: Users) {
        repository.addUser(user) { success, error ->
            if (success) {
                _operationStatus.postValue(OperationResult.Success)
            } else {
                _operationStatus.postValue(
                    OperationResult.Error(error ?: "Unknown Firebase Error")
                )
            }
        }
    }
    fun updateUser(userId: String, userMap: Map<String, Any>) {

        repository.updateUser(userId, userMap) { success, error ->

            if (success) {
                _operationStatus.postValue(OperationResult.Success)
            } else {
                _operationStatus.postValue(
                    OperationResult.Error(error ?: "Update failed")
                )
            }
        }
    }


    fun deleteUser(id: String) {

        repository.deleteUser(id) { success, message ->

            if (success) {
                _operationStatus.value = OperationResult.Success
            } else {
                _operationStatus.value =
                    OperationResult.Error(message ?: "Delete failed")
            }
        }
    }

}
