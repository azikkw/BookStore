package com.example.bookstore.screens

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.data.User
import com.example.bookstore.viewmodels.BooksViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun BookItemAdmin (
    booksViewModel : BooksViewModel,
    navController: NavController,
    book: Book,
) {
    Row (
        modifier = Modifier
            .padding(bottom = 15.dp)
            .clip(RoundedCornerShape(18.dp))
            .fillMaxWidth()
            .background(Color(247, 247, 247, 255))
            .padding(20.dp)
    ) {
        Button (
            onClick = { bookDetailAdmin(book, booksViewModel, navController) },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(Color(236, 236, 236, 255))
        ) {
            AsyncImage (
                model = book.url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(80.dp)
                    .height(130.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Column (
            modifier = Modifier
                .padding(start = 15.dp)
                .height(130.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column (
                modifier = Modifier.fillMaxWidth()
            ) {
                Text (
                    text = if(book.title.length > 24) book.title.substring(0, 24) + " ..." else book.title,
                    color = Color(30, 30, 30),
                    fontSize = 21.sp,
                    fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text (
                    text = if(book.description.length > 40) book.description.substring(0, 37) + " ..." else book.description,
                    color = Color(162, 162, 162, 255),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.nunitosemibold))
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text (
                    text = book.cost.toString() + " ₸",
                    color = Color(30, 30, 30, 255),
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(R.font.nunitoextrabold))
                )
                IconButton (
                    onClick = { deleteBook(book.bookId, navController, booksViewModel, book) },
                    modifier = Modifier
                        .padding(start = 70.dp)
                ) {
                    Icon (
                        Icons.Rounded.Delete,
                        contentDescription = "Delete",
                        Modifier.size(26.dp),
                        tint = Color(40, 40, 40)
                    )
                }
            }
        }
    }
}

fun bookDetailAdmin(book: Book, booksViewModel : BooksViewModel, navController: NavController) {
    booksViewModel.bookDetail.bookId = book.bookId
    booksViewModel.bookDetail.ownerId = book.ownerId
    booksViewModel.bookDetail.url = book.url
    booksViewModel.bookDetail.title = book.title
    booksViewModel.bookDetail.description = book.description
    booksViewModel.bookDetail.cost = book.cost
    navController.navigate("bookDetail")
}

fun deleteBook(bookId: String, navController: NavController, booksViewModel : BooksViewModel, book : Book){
    booksViewModel.database.collection("books").document(bookId)
        .delete()
        .addOnSuccessListener {
            Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!")
            booksViewModel.books.remove(book)
            navController.navigate("home")
            navController.popBackStack()
        }
        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
}









