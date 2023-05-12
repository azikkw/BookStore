package com.example.bookstore.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.viewmodels.BooksViewModel
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("MutableCollectionMutableState")
@Composable
fun AdminBooksScreen (
    navController: NavController,
    booksViewModel: BooksViewModel
) {
    val filterDialogActive =  remember { mutableStateOf(false) }
    booksViewModel.isFiltered()
    var books by remember { mutableStateOf(mutableListOf<Book>()) }
    books = booksViewModel.books

    fun filterDialog(){
        filterDialogActive.value = !filterDialogActive.value
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        Text (
            text = "Admin Panel",
            color = Color(30, 30, 30),
            fontSize = 40.sp,
            fontFamily = FontFamily(Font(R.font.nunitoblack)),
            modifier = Modifier.padding(top = 40.dp, start = 25.dp, end = 25.dp, bottom = 15.dp)
        )

        Row (
            modifier = Modifier
                .padding(start = 25.dp, end = 5.dp, bottom = 30.dp)
                .fillMaxWidth()
                .height(27.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text (
                text = "List of books",
                color = Color(172, 172, 172, 255),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunitobold))
            )
            Row {
                IconButton (
                    onClick = { navController.navigate("search") },
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .clip(shape = RoundedCornerShape(14.dp))
                        .background(Color(128, 171, 111))
                ) {
                    Icon (
                        Icons.Default.Search,
                        contentDescription = "Search",
                        Modifier.size(25.dp),
                        tint = Color.White
                    )
                }

                IconButton (
                    onClick = {
                        booksViewModel.isFilter = false
                        booksViewModel.handmadeInit()
                    }
                ) {
                    Icon (
                        painterResource(id = R.drawable._87834_200),
                        contentDescription = "No Filter",
                        modifier = Modifier
                            .size(27.dp)
                            .offset(x = 10.dp),
                        tint = Color.Black
                    )
                }
                IconButton (
                    onClick = { filterDialog() }
                ) {
                    Icon (
                        painterResource(id = R.drawable.ic_round_tune_24),
                        contentDescription = "Filter",
                        modifier = Modifier
                            .size(27.dp)
                            .offset(x = 10.dp),
                        tint = Color.Black
                    )
                }
            }
        }

        LazyColumn (modifier = Modifier.padding(start = 25.dp, end = 25.dp, bottom = 40.dp)) {
            items(books) { b ->
                BookItemAdmin (
                    booksViewModel = booksViewModel,
                    navController = navController,
                    book = b,
                )
            }
        }
    }


    if (filterDialogActive.value) FilterDialog({ filterDialog() }, booksViewModel)
}