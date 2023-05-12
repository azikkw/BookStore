package com.example.bookstore.screens

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.data.Seller
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.SellerViewModel

@Composable
fun StoreItem (
    navController: NavController,
    store: Seller,
    sellerViewModel: SellerViewModel,
    booksViewModel : BooksViewModel,
    signOut : ()->Unit
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
            onClick = { storeDetail(store, sellerViewModel, navController) },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(Color(236, 236, 236, 255))
        ) {
            Icon (
                painterResource(id = R.drawable.ic_round_store_24),
                contentDescription = "Store",
                Modifier.size(60.dp),
                tint = Color(40, 40, 40)
            )
        }
        Column (
            modifier = Modifier
                .padding(start = 15.dp)
                .fillMaxWidth(),
        ) {
            Text (
                text = if(store.name.length > 24) store.name.substring(0, 24) + " ..." else store.name,
                color = Color(30, 30, 30),
                fontSize = 21.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text (
                text = store.address,
                color = Color(30, 30, 30),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.nunitomedium)),
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Text (
                text = store.phone,
                color = Color(30, 30, 30),
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.nunitomedium)),
                modifier = Modifier.padding(bottom = 5.dp)
            )
        }
    }
}

//fun storeDetail(store: Seller, sellerViewModel : SellerViewModel, navController: NavController) {
//    sellerViewModel.sellerDetail.sellerId = store.sellerId
//    sellerViewModel.sellerDetail.name = store.name
//    sellerViewModel.sellerDetail.address = store.address
//    sellerViewModel.sellerDetail.email = store.email
//    sellerViewModel.sellerDetail.phone = store.phone
//    navController.navigate("storeProfile")  // storeProfile
//}

