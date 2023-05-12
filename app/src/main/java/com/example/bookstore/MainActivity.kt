package com.example.bookstore

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookstore.data.Book
import com.example.bookstore.data.Seller
import com.example.bookstore.data.User
import com.example.bookstore.screens.*
import com.example.bookstore.ui.theme.BookstoreTheme
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.SellerViewModel
import com.example.bookstore.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    @SuppressLint("MutableCollectionMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val booksViewModel: BooksViewModel by viewModels()
        val userViewModel: UserViewModel by viewModels()
        val sellerViewModel: SellerViewModel by viewModels()
        setContent {
            BookstoreTheme {
                BookStore (
                    booksViewModel = booksViewModel,
                    userViewModel = userViewModel,
                    sellerViewModel = sellerViewModel,
                    applicationContext = applicationContext
                )
            }
        }
    }
}


@SuppressLint("MutableCollectionMutableState")
@Composable
fun BookStore (
    booksViewModel: BooksViewModel,
    userViewModel: UserViewModel,
    sellerViewModel: SellerViewModel,
    applicationContext: Context
) {
    val navController = rememberNavController()

    fun signOut(){
        userViewModel.currentUser = User("", "", "", "", "", 0.0)
        sellerViewModel.currentSeller = Seller("", "", "", "", "")
        booksViewModel.auth.signOut()
        navController.navigate("welcome")
    }

    NavHost(navController = navController, "welcome") {
        composable("welcome") {
            WelcomeScreen(navController)
        }
        composable("login") {
            LoginScreen(navController, booksViewModel, userViewModel, sellerViewModel, applicationContext)
        }
        composable("registration") {
            RegistrationScreen(navController, booksViewModel, applicationContext)
        }
        composable("registration-for-stores") {
            RegistrationForStoresScreen(navController, booksViewModel, applicationContext)
        }
        composable("main") {
            MainScreen(booksViewModel, userViewModel, sellerViewModel, applicationContext) { signOut() }
        }
    }
}