package com.example.bookstore.screens

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.data.CartBook
import com.example.bookstore.data.User
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.UserViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@SuppressLint("MutableCollectionMutableState")
@Composable
fun CartScreen (
    navController: NavController,
    booksViewModel: BooksViewModel,
    userViewModel: UserViewModel,
    context: Context,
    cartItems: MutableList<CartBook>
) {
    var totalCost = 0; cartItems.forEach { book -> totalCost += (book.cost.toInt() * book.quantity) }
    var orderedBooks = 0; cartItems.forEach { book -> orderedBooks += book.quantity }

    val user = userViewModel.currentUser

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(250, 250, 250))
    ) {
        Column {
            // Top bar
            Surface (
                modifier = Modifier.padding(bottom = 15.dp),
                elevation = 9.dp
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(255, 255, 255))
                        .padding(top = 15.dp, bottom = 15.dp, start = 20.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text (
                        text = "Cart",
                        fontSize = 19.sp,
                        fontFamily = FontFamily(Font(R.font.quicksandbold)),
                        color = Color(30, 30, 30)
                    )
                    // Cart items total cost
                    Text (
                        text = if(cartItems.isEmpty()) "0₸" else "$totalCost₸",
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                        color = Color(129, 129, 129, 255)
                    )
                }
            }

            if(cartItems.isEmpty()) {
                Text (
                    text = "There are no books yet",
                    fontSize = 24.sp,
                    fontFamily = FontFamily(Font(R.font.quicksandbold)),
                    color = Color(30, 30, 30),
                    modifier = Modifier
                        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                )
                TextButton (
                    onClick = { navController.navigate("home") },
                    modifier = Modifier
                        .padding(start = 13.dp, end = 20.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.Start)
                        .offset(y = (-3).dp)
                ) {
                    Text (
                        text = "To select books, go to ",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                        color = Color(30, 30, 30),
                        letterSpacing = 0.sp
                    )
                    Text (
                        text ="Home",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.quicksandbold)),
                        textDecoration = TextDecoration.Underline,
                        color = Color(30, 30, 30),
                        letterSpacing = 0.sp
                    )
                }
            }
            else {
                // Cart items part
                LazyColumn (
                    modifier = Modifier.padding(bottom = 70.dp)
                ) {
                    items(cartItems) { book ->
                        CartItem(navController, booksViewModel, userViewModel, cartItems, book)
                    }

                    // Total information part
                    item {
                        Column (
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .background(Color(255, 255, 255))
                                .padding(20.dp)
                        ) {
                            // About books
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text (
                                    text = "Books in order",
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.quicksandmedium)),
                                    color = Color(30, 30, 30)
                                )
                                Text (
                                    text = "$orderedBooks",
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.quicksandmedium)),
                                    color = Color(30, 30, 30)
                                )
                            }
                            Row (
                                modifier = Modifier
                                    .padding(top = 3.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text (
                                    text = "Total",
                                    fontSize = 19.sp,
                                    fontFamily = FontFamily(Font(R.font.quicksandbold)),
                                    color = Color(30, 30, 30)
                                )
                                Text (
                                    text = "$totalCost₸",
                                    fontSize = 19.sp,
                                    fontFamily = FontFamily(Font(R.font.quicksandbold)),
                                    color = Color(30, 30, 30)
                                )
                            }
                            // About your balance
                            Row (
                                modifier = Modifier
                                    .padding(top = 15.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text (
                                    text = "On balance",
                                    fontSize = 15.sp,
                                    fontFamily = FontFamily(Font(R.font.quicksandmedium)),
                                    color = Color(30, 30, 30)
                                )
                                Text (
                                    text = if(user.balance - totalCost.toDouble() > 0) "${user.balance}₸" else "Insufficient funds",
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.quicksandmedium)),
                                    color = Color(30, 30, 30)
                                )
                            }
                            if(user.balance - totalCost.toDouble() > 0) {
                                Row (
                                    modifier = Modifier
                                        .padding(top = 1.dp)
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text (
                                        text = "Remains",
                                        fontSize = 19.sp,
                                        fontFamily = FontFamily(Font(R.font.quicksandbold)),
                                        color = Color(30, 30, 30)
                                    )
                                    Text (
                                        text = "${user.balance - totalCost.toDouble()}₸",
                                        fontSize = 19.sp,
                                        fontFamily = FontFamily(Font(R.font.quicksandbold)),
                                        color = Color(30, 30, 30)
                                    )
                                }
                            }
                            // Buy button
                            Button (
                                onClick = { if(user.balance - totalCost.toDouble() > 0) buyBooks(user, cartItems, user.balance - totalCost.toDouble(), navController, context) },
                                modifier = Modifier
                                    .padding(top = 18.dp)
                                    .fillMaxWidth()
                                    .height(65.dp)
                                    .clip(shape = RoundedCornerShape(12.dp)),
                                colors = if(user.balance - totalCost.toDouble() > 0) ButtonDefaults.buttonColors(Color(30, 30, 30)) else ButtonDefaults.buttonColors(Color(140, 140, 140))
                            ) {
                                Text (
                                    text = "Buy",
                                    fontSize = 20.sp,
                                    fontFamily = FontFamily(Font(R.font.quicksandbold)),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun buyBooks (
    user: User,
    cartItems: MutableList<CartBook>,
    remains: Double,
    navController: NavController,
    context: Context
) {
    var counter = 0
    cartItems.forEach { book ->
        Firebase.firestore.collection("cart")
            .document(user.userId + book.bookId)
            .delete()
        Firebase.firestore.collection("books")
            .document(book.bookId)
            .update(
                mapOf(
                    "buyingCount" to book.buyingCount.plus(1)
                )
            )
        counter++
    }
    if(counter == cartItems.size) {
        Firebase.firestore.collection("users")
            .document(user.userId)
            .update(
                mapOf(
                    "balance" to remains
                )
            )
            .addOnSuccessListener {
                Toast.makeText(context, "The purchase was made successfully!", Toast.LENGTH_SHORT).show()
                user.balance = remains
                cartItems.clear()
                navController.navigate("cart")
                navController.popBackStack()
            }
    }
}