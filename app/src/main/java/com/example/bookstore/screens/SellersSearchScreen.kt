package com.example.bookstore.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.data.Seller
import com.example.bookstore.data.User
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.SellerViewModel
import java.util.*

@SuppressLint("MutableCollectionMutableState")
@Composable
fun SellersSearchScreen (
    navController: NavController,
    sellerViewModel: SellerViewModel,
    booksViewModel: BooksViewModel,
    signOut : ()->Unit
) {
    val sellers = sellerViewModel.sellers

    var searchText by remember { mutableStateOf("") }
    var notFound by remember { mutableStateOf("") }

    var sellersList by remember {
        mutableStateOf<MutableList<Seller>>(mutableListOf())
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 50.dp)
    ) {
        Row (
            modifier = Modifier
                .padding(start = 15.dp, end = 20.dp, bottom = 30.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton (
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, "contentDescription", tint = Color.Black)
            }
            Text (
                text = "Search sellers",
                fontSize = 21.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                color = Color(30, 30, 30)
            )
        }

        TextField (
            value = searchText,
            onValueChange = {
                newValue -> searchText = newValue
                if(searchText.isNotEmpty()) {
                    sellersList = sellers.filter { s ->
                        s.name.toLowerCase(Locale.ROOT).contains(searchText.toLowerCase(Locale.ROOT))
                    } as MutableList<Seller>
                    notFound = if(sellersList.isEmpty()) "Nothing was found for your query" else ""
                }
                else {
                    sellersList.clear()
                    notFound = ""
                }
            },
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(15.dp))
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
                    text = "Search book by title",
                    color = Color(0, 0, 0, 90),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.nunitosemibold))
                )
            },
            trailingIcon = {
                Icon (
                    Icons.Default.Search,
                    contentDescription = "Search",
                    Modifier.size(25.dp),
                    tint = Color.Black
                )
            },
        )

        LazyColumn (
            modifier = Modifier
                .padding(top = 15.dp, start = 20.dp, end = 20.dp, bottom = 45.dp)
        ) {
            items(sellersList) { s ->
                StoreItem (
                    navController = navController,
                    store = s,
                    sellerViewModel = sellerViewModel,
                    booksViewModel = booksViewModel
                ) {signOut()}
            }
            // If nothing found
            item {
                Box {
                    Text (
                        text = notFound,
                        color = Color(120, 120, 120),
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold)),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 10.dp).fillMaxWidth()
                    )
                }
            }
        }
    }
}