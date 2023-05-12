package com.example.bookstore.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.icons.rounded.Star
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.data.Comment
import com.example.bookstore.data.Rating
import com.example.bookstore.viewmodels.BooksViewModel

@Composable
fun RateBookScreen (
    navController: NavController,
    booksViewModel: BooksViewModel
) {
    var bookRate by remember { mutableStateOf(0) }
    val book = booksViewModel.bookDetail

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255, 247, 237))
    ) {
        AsyncImage (
            model = book.url,
            contentDescription = "Book detail background image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(Color(30, 30, 30, 200))
        )

        Column (
            modifier = Modifier
                .padding(top = 15.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top navigation part
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton (
                    onClick = { navController.popBackStack() }
                ) {
                    Icon (
                        Icons.Rounded.ArrowBack,
                        contentDescription = "Back",
                        Modifier
                            .padding(end = 25.dp)
                            .size(23.dp),
                        tint = Color.White
                    )
                }
                Text (
                    text = "Rate book",
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(R.font.quicksandbold)),
                    color = Color(255, 255, 255),
                )
            }

            // Book title part
            Text (
                text = book.title,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.quicksandbold)),
                color = Color(255, 255, 255),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .wrapContentHeight(Alignment.Bottom)
            )

            // Book rate part
            Text (
                text = "Rate from 1 to 5",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.quicksandbold)),
                color = Color(30, 30, 30),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 60.dp, bottom = 5.dp)
                    .fillMaxWidth()
            )
            LazyRow {
                items(bookRate) {
                    IconButton (
                        onClick = { bookRate = (it + 1) }
                    ) {
                        Icon (
                            Icons.Rounded.Star,
                            contentDescription = "Search",
                            Modifier.size(40.dp),
                            tint = Color(255, 153, 0)
                        )
                    }
                }
                items(5 - bookRate) {
                    IconButton (
                        onClick = { bookRate = (it + bookRate + 1) }
                    ) {
                        Icon (
                            painterResource(id = R.drawable.ic_round_star_border_24),
                            contentDescription = "Search",
                            Modifier.size(40.dp),
                            tint = Color(255, 153, 0)
                        )
                    }
                }
            }

            // Save impression button
            Button (
                onClick = {
                    addRating(booksViewModel,bookRate)
                    booksViewModel.i_Im_rated_this_book = true
                    booksViewModel.myRatingInBook = Rating(booksViewModel.user.userId,booksViewModel.bookDetail.bookId , bookRate)
                    booksViewModel.ratedPeople ++
                    navController.popBackStack()
                },
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth()
                    .height(65.dp)
                    .clip(shape = RoundedCornerShape(12.dp)),
                colors = ButtonDefaults.buttonColors(Color(30, 30, 30))
            ) {
                Text (
                    text = "Save my rate",
                    fontSize = 17.sp,
                    fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                    color = Color(255, 247, 237)
                )
            }
        }
    }
}



fun addRating(booksViewModel: BooksViewModel , ratValue : Int){
    val ratingItem = Rating(booksViewModel.user.userId , booksViewModel.bookDetail.bookId , ratValue)
    val database = booksViewModel.database
    database.collection("ratings")
        .document(booksViewModel.user.userId + booksViewModel.bookDetail.bookId)
        .set(ratingItem)
        .addOnSuccessListener {
            println("SUCCES")
        }
        .addOnFailureListener {
            println("Failed to add comment:")
        }
}

