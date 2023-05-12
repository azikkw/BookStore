package com.example.bookstore.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.data.Seller
import com.example.bookstore.viewmodels.BooksViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun UpdateBookScreen (
    navController: NavController,
    booksViewModel: BooksViewModel,
    applicationContext: Context,
) {
    var book = booksViewModel.bookDetail
    var books = booksViewModel.books

    var titleText by remember { mutableStateOf(book.title) }
    var descriptionText by remember { mutableStateOf(book.description) }
    var authorText by remember { mutableStateOf(book.author) }
    var costText by remember { mutableStateOf(book.cost.toString()) }

    Column (
        modifier = Modifier
            .padding(bottom = 50.dp)
            .fillMaxSize()
            .padding(top = 35.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row (
            modifier = Modifier
                .padding(start = 20.dp, end = 25.dp, bottom = 30.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack("home", inclusive = false) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, "contentDescription")
            }
            Text(
                text = "Update book",
                fontSize = 21.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                color = Color(30, 30, 30)
            )
        }

        Text (
            text = book.title,
            color = Color(30, 30, 30),
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.nunitoblack)),
            modifier = Modifier.padding(top = 15.dp, start = 25.dp, end = 25.dp)
        )

        Column(modifier = Modifier.padding(top = 30.dp, start = 25.dp, end = 25.dp)) {
            Text (
                text = "Book title",
                color = Color(0, 0, 0),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                modifier = Modifier.padding(bottom = 15.dp)
            )
            TextField (
                value = titleText,
                onValueChange = { newValue -> titleText = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(22.dp))
                    .background(Color(240, 240, 240, 255))
                    .padding(start = 5.dp, end = 5.dp),
                colors = TextFieldDefaults.textFieldColors (
                    textColor = Color(0, 0, 0),
                    backgroundColor = Color(240, 240, 240, 255),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(
                        text = "New book title",
                        color = Color(0, 0, 0, 90),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                }
            )
        }
        Column(modifier = Modifier.padding(top = 20.dp, start = 25.dp, end = 25.dp)) {
            Text (
                text = "Book description",
                color = Color(0, 0, 0),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                modifier = Modifier.padding(bottom = 15.dp)
            )
            TextField (
                value = descriptionText,
                onValueChange = { newValue -> descriptionText = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(22.dp))
                    .background(Color(240, 240, 240, 255))
                    .padding(start = 5.dp, end = 5.dp),
                colors = TextFieldDefaults.textFieldColors (
                    textColor = Color(0, 0, 0),
                    backgroundColor = Color(240, 240, 240, 255),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                placeholder = {
                    Text(
                        text = "New book description",
                        color = Color(0, 0, 0, 90),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                }
            )
            Column(modifier = Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Book author",
                    color = Color(0, 0, 0),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                TextField(
                    value = authorText,
                    onValueChange = { newValue -> authorText = newValue },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(22.dp))
                        .background(Color(240, 240, 240, 255))
                        .padding(start = 5.dp, end = 5.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color(0, 0, 0),
                        backgroundColor = Color(240, 240, 240, 255),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    placeholder = {
                        Text(
                            text = "New book author",
                            color = Color(0, 0, 0, 90),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunitosemibold))
                        )
                    }
                )
            }
            Column(modifier = Modifier.padding(top = 20.dp)) {
                Text(
                    text = "Book cost",
                    color = Color(0, 0, 0),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                TextField(
                    value = costText,
                    onValueChange = { newValue -> costText = newValue },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(22.dp))
                        .background(Color(240, 240, 240, 255))
                        .padding(start = 5.dp, end = 5.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color(0, 0, 0),
                        backgroundColor = Color(240, 240, 240, 255),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    placeholder = {
                        Text(
                            text = "New book cost",
                            color = Color(0, 0, 0, 90),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunitosemibold))
                        )
                    }
                )
            }
        }

        Button(
            onClick = {
                updateBook (
                    book = book,
                    title = titleText,
                    description = descriptionText,
                    author = authorText,
                    cost = costText.toDouble(),
                    applicationContext = applicationContext,
                    books = books
                )
            },
            modifier = Modifier
                .padding(top = 40.dp, start = 25.dp, end = 25.dp, bottom = 30.dp)
                .fillMaxWidth()
                .height(60.dp)
                .clip(shape = RoundedCornerShape(22.dp)),
            colors = ButtonDefaults.buttonColors(Color(128, 171, 111))
        )
        {
            Text(
                text = "Update book",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.nunitoblack)),
                color = Color.White
            )
        }
    }
}

fun updateBook (
    book: Book,
    title: String,
    description: String,
    author: String,
    cost: Double,
    applicationContext: Context,
    books: MutableList<Book>,
) {
    Firebase.firestore.collection("books")
        .document(book.bookId)
        .update(
            mapOf(
                "title" to title,
                "description" to description,
                "author" to author,
                "cost" to cost
            )
        )
        .addOnSuccessListener {
            Toast.makeText(applicationContext, "Book successfully updated!", Toast.LENGTH_SHORT).show()
            books.filter { b ->
                b.bookId == book.bookId
            }.map { b ->
                b.title = title
                b.description = description
                b.author = author
                b.cost = cost
            }
            book.title = title
            book.author = author
            book.description = description
            book.cost = cost
        }
}