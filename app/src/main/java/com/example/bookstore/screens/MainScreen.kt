package com.example.bookstore.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bookstore.data.Book
import com.example.bookstore.data.CartBook
import com.example.bookstore.data.User
import com.example.bookstore.navigation.BottomNavigation
import com.example.bookstore.navigation.NavigationGraph
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.SellerViewModel
import com.example.bookstore.viewmodels.UserViewModel
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("MutableCollectionMutableState")
@Composable
fun MainScreen (
    booksViewModel : BooksViewModel,
    userViewModel: UserViewModel,
    sellerViewModel: SellerViewModel,
    applicationContext: Context,
    signOut : ()->Unit
){
    val navController = rememberNavController()

    Scaffold (
        bottomBar = { BottomNavigation(navController, userViewModel, booksViewModel, sellerViewModel) { signOut() } }
    ) {
        Column (
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {}
        NavigationGraph (
            booksViewModel,
            userViewModel,
            sellerViewModel,
            navController,
            applicationContext,
//            cartItems,
        ) { signOut() }
    }
}