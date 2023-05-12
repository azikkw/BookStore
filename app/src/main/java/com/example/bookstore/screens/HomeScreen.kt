package com.example.bookstore.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.data.User
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen (
    navController: NavController,
    booksViewModel: BooksViewModel,
    userViewModel: UserViewModel,
    books : MutableList<Book>
) {
    val user = userViewModel.currentUser

    val filterDialogActive =  remember { mutableStateOf(false) }
    fun filterDialog() {
        filterDialogActive.value = !filterDialogActive.value
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 25.dp, start = 20.dp, end = 20.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if(user.userId != "") {
                TextButton(
                    onClick = { navController.navigate("balance") },
                    modifier = Modifier.offset(x = (-12).dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_round_account_balance_wallet_24),
                        contentDescription = "Search",
                        Modifier.padding(end = 6.dp).size(26.dp),
                        tint = Color.Black
                    )
                    Text(
                        text = "${user.balance}₸",
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold)),
                        color = Color(30, 30, 30),
                        letterSpacing = 0.sp
                    )
                }
            } else {
                Text(
                    text = "Home",
                    fontSize = 21.sp,
                    fontFamily = FontFamily(Font(R.font.nunitoblack)),
                    color = Color(30, 30, 30)
                )
            }
            IconButton (
                onClick = { navController.navigate("search") },
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(Color(128, 171, 111))
            ) {
                Icon (
                    Icons.Default.Search,
                    contentDescription = "Search",
                    Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }

        Text (
            text = "Book Store",
            color = Color(30, 30, 30),
            fontSize = 40.sp,
            fontFamily = FontFamily(Font(R.font.nunitoblack)),
            modifier = Modifier
                .padding(top = 35.dp, bottom = 8.dp)
                .offset(x = (-3).dp)
        )

        Row (
            modifier = Modifier
                .padding(bottom = 22.dp)
                .fillMaxWidth()
                .height(27.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text (
                text = "List of books",
                color = Color(172, 172, 172, 255),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunitobold))
            )
            Row {
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
        LazyColumn(modifier = Modifier.padding(bottom = 70.dp)) {
            items(books) { b ->
                BookItem (
                    navController = navController,
                    booksViewModel = booksViewModel,
                    book = b,
                )
            }
        }
    }

    if(filterDialogActive.value) FilterDialog({ filterDialog() }, booksViewModel)
}