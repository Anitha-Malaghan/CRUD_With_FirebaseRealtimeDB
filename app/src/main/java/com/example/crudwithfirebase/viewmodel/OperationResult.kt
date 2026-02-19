package com.example.crudwithfirebase.viewmodel

sealed class OperationResult {
    object Success : OperationResult()
    data class Error(val message: String) : OperationResult()
}