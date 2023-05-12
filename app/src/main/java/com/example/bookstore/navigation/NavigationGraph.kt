package com.example.bookstore.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookstore.data.User
import com.example.bookstore.data.Book
import com.example.bookstore.data.CartBook
import com.example.bookstore.screens.*
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.SellerViewModel
import com.example.bookstore.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("MutableCollectionMutableState")
@Composable
fun NavigationGraph (
    booksViewModel: BooksViewModel,
    userViewModel: UserViewModel,
    sellerViewModel: SellerViewModel,
    navController: NavHostController,
    applicationContext: Context,
//    cartItems: MutableList<CartBook>,
    signOut : ()->Unit
) {
    booksViewModel.getCart(userViewModel.currentUser.userId)
    var cartItems by remember { mutableStateOf(mutableListOf<CartBook>()) }
    cartItems = booksViewModel.userCart

    var books by remember { mutableStateOf(mutableListOf<Book>()) }
    books = booksViewModel.books

    NavHost (
        navController = navController,
        startDestination = BottomNavItem.Home.route
    ) {
        composable(BottomNavItem.Home.route) {
            if(userViewModel.isAdmin()) {
                AdminBooksScreen(navController, booksViewModel)
            } else {
                HomeScreen(navController, booksViewModel, userViewModel, books)
            }
        }
        composable(BottomNavItem.StoreList.route) {
            if(userViewModel.isAdmin()) {
                AdminStoresScreen(navController, sellerViewModel, booksViewModel)
            } else {
                StoreListScreen(navController, sellerViewModel, booksViewModel) { signOut() }
            }
        }
        composable(BottomNavItem.Profile.route) {
            if(sellerViewModel.currentSeller.sellerId.isNotEmpty()){
                SellerProfileScreen(navController, booksViewModel, sellerViewModel, books) { signOut() }
            } else if(userViewModel.currentUser.userId.isNotEmpty()){
                ProfileScreen(navController, userViewModel) { signOut() }
            }
        }
        composable(BottomNavItem.AddBook.route){
            AddBookScreen(navController, booksViewModel, sellerViewModel, applicationContext)
        }
        composable("update-book") {
            UpdateBookScreen(navController, booksViewModel, applicationContext )
        }
        composable(BottomNavItem.Cart.route) {
            CartScreen(navController, booksViewModel, userViewModel, applicationContext, cartItems)
        }
        composable("bookDetail") {
            BookDetails(navController, booksViewModel, userViewModel, sellerViewModel, applicationContext, cartItems, books)
        }
        composable("rate-book") {
            RateBookScreen(navController, booksViewModel)
        }
        composable("search") {
            SearchScreen(navController, booksViewModel, books)
        }
        composable("searchStore") {
            SellersSearchScreen (navController, sellerViewModel, booksViewModel) { signOut() }
        }
        composable("balance") {
            BalanceScreen(navController, userViewModel, applicationContext)
        }
        composable("storeProfile") {
            SellerProfileScreen(navController, booksViewModel, sellerViewModel, books) { signOut() }
        }
    }
}
