package com.example.bookstore.screens

import android.content.ContentValues.TAG
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
import com.google.firebase.ktx.Firebase

@Composable
fun StoreItemAdmin (
    navController: NavController,
    store: Seller,
    sellerViewModel: SellerViewModel,
    booksViewModel : BooksViewModel,
    stores : MutableList<Seller>,
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
//            Image (
//                painter = painterResource(R.drawable.profile_image),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .width(80.dp)
//                    .height(130.dp)
//                    .clip(RoundedCornerShape(8.dp))
//            )
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
                .height(130.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
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

            IconButton (
                onClick = { deleteStore(store.sellerId, sellerViewModel, booksViewModel, navController, store) },
                modifier = Modifier
                    .padding(start = 155.dp)

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

fun storeDetail(store: Seller, sellerViewModel : SellerViewModel, navController: NavController) {
    sellerViewModel.sellerDetail.sellerId = store.sellerId
    sellerViewModel.sellerDetail.name = store.name
    sellerViewModel.sellerDetail.address = store.address
    sellerViewModel.sellerDetail.email = store.email
    sellerViewModel.sellerDetail.phone = store.phone
    navController.navigate("storeProfile")  // storeProfile
}

fun deleteStore(sellerId: String, sellerViewModel: SellerViewModel, booksViewModel : BooksViewModel, navController: NavController, store : Seller){
    booksViewModel.database.collection("sellers")
        .document(sellerId)
        .delete()
        .addOnSuccessListener {
            for (book in booksViewModel.books){
                if(book.ownerId == sellerId){
                    val docRef = booksViewModel.database.collection("books").document(book.bookId)
                        .delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "DocumentSnapshot successfully deleted!")
                        }
                        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
                }
            }
            Log.d(TAG, "DocumentSnapshot successfully deleted!")
            sellerViewModel.sellers.remove(store)
            navController.navigate("stores")
            navController.popBackStack()
        }
        .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
}
