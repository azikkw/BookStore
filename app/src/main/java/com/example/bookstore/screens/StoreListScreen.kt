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
import androidx.compose.ui.Alignment
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
import com.example.bookstore.data.Seller
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.SellerViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

@SuppressLint("MutableCollectionMutableState")
@Composable
fun StoreListScreen (
    navController: NavController,
    sellerViewModel: SellerViewModel,
    booksViewModel : BooksViewModel,
    signOut : ()->Unit
) {
    var stores by remember { mutableStateOf(mutableListOf<Seller>()) }
    stores = sellerViewModel.sellers

    val filterDialogActive =  remember { mutableStateOf(false) }
    fun filterDialog(){
        filterDialogActive.value = !filterDialogActive.value
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp, start = 20.dp, end = 20.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Home",
                fontSize = 21.sp,
                fontFamily = FontFamily(Font(R.font.nunitoblack)),
                color = Color(30, 30, 30)
            )
            IconButton (
                onClick = { navController.navigate("searchStore") },
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
            text = "Stores",
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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text (
                text = "List of stores",
                color = Color(172, 172, 172, 255),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunitobold))
            )
            Row {
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

        LazyColumn (modifier = Modifier.padding(bottom = 60.dp)) {
            items(stores) { b ->
                StoreItem (
                    navController = navController,
                    store = b,
                    sellerViewModel = sellerViewModel,
                    booksViewModel = booksViewModel,
                ) {signOut()}
            }
        }
    }
    if (filterDialogActive.value) FilterDialog({ filterDialog() }, booksViewModel)
}