package com.example.bookstore.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.data.Book
import com.example.bookstore.data.Comment
import com.example.bookstore.data.Rating
import com.example.bookstore.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@SuppressLint("MutableCollectionMutableState")
@HiltViewModel
class UserViewModel
@Inject
constructor(): ViewModel(){
    var users by mutableStateOf<MutableList<User>>(mutableListOf())
    var currentUser by mutableStateOf(User("", "", "", "",  "",0.0))

    init {
        viewModelScope.launch {
            users = getUserList().toMutableList()
        }
    }

    fun setCurrentUser(id : String) {
        viewModelScope.launch {
            getById(id)
        }
    }

    fun isAdmin() : Boolean {
        return currentUser.userType == "admin"
    }

    private suspend fun getById(id : String) {
        Firebase.firestore.collection("users").get().await().map { user ->
            val userOb = user.toObject(User::class.java)
            if(userOb.userId == id) {
                currentUser = userOb
            }
        }
    }

    private suspend fun getUserList() : List<User> {
        return Firebase.firestore.collection("users").get().await().map { user ->
            user.toObject(User::class.java)
        }
    }
}