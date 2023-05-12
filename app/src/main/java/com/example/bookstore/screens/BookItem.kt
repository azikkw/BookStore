package com.example.bookstore.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.data.CartBook
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.UserViewModel

@Composable
fun BookItem (
    navController: NavController,
    booksViewModel: BooksViewModel,
    book: Book,
) {
    val bookDetail: Book = booksViewModel.bookDetail

    Row (
        modifier = Modifier
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(Color(247, 247, 247, 255))
            .padding(15.dp)
    ) {
        Button (
            onClick = {
                bookDetail(book, bookDetail, navController)
                booksViewModel.getComment(book)
                booksViewModel.getRate(book)
                booksViewModel.getMyRating(booksViewModel.user.userId , booksViewModel.bookDetail.bookId)
            },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.clip(RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(Color(236, 236, 236, 255))
        ) {
            AsyncImage (
                model = book.url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(90.dp)
                    .height(145.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Column (
            modifier = Modifier
                .padding(start = 15.dp)
                .height(145.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column (
                modifier = Modifier.fillMaxWidth()
            ) {
                Text (
                    text = if(book.title.length > 30) book.title.substring(0, 30) + " ..." else book.title,
                    color = Color(30, 30, 30),
                    fontSize = 21.sp,
                    fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                    modifier = Modifier.padding(bottom = 3.dp)
                )
                Text (
                    text = if(book.description.length > 40) book.description.substring(0, 40) + " ..." else book.description,
                    color = Color(162, 162, 162, 255),
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.nunitosemibold))
                )
            }
            Text (
                text = book.cost.toString() + " ₸",
                color = Color(30, 30, 30, 255),
                fontSize = 19.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold))
            )
        }
    }
}

@Composable
fun CartItem (
    navController: NavController,
    booksViewModel: BooksViewModel,
    userViewModel: UserViewModel,
    cartItems: MutableList<CartBook>,
    book: CartBook
) {
    val bookDetail: Book = booksViewModel.bookDetail

    Row (
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .background(Color(255, 255, 255))
            .padding(15.dp)
    ) {
        // Book poster part
        Button (
            onClick = { bookDetail(book, bookDetail, navController) },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .padding(end = 15.dp)
                .clip(RoundedCornerShape(7.dp)),
            colors = ButtonDefaults.buttonColors(Color(236, 236, 236, 255))
        ) {
            AsyncImage (
                model = book.url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .height(160.dp)
                    .clip(RoundedCornerShape(7.dp))
            )
        }
        // Book information part
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text (
                    text = if(book.title.length > 38) book.title.substring(0, 38) + " ..." else book.title,
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                    color = Color(30, 30, 30)
                )
                Text (
                    text = "Quantity: ${book.quantity}",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                    color = Color(30, 30, 30, 200),
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text (
                    text = "${book.cost.toInt()}₸",
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(R.font.quicksandbold)),
                    color = Color(30, 30, 30),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            // Cart item managing part
            Row (
                modifier = Modifier
                    .align(Alignment.End)
                    .offset(x = 10.dp, y = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton (
                    onClick = {
                        if(book.quantity > 1) decreaseQuantity(booksViewModel, userViewModel, book, navController)
                        else deleteBookFromCart(booksViewModel, userViewModel, cartItems, book, navController)
                    }
                ) {
                    Icon (
                        painterResource(id = R.drawable.ic_round_remove_circle_outline_24),
                        contentDescription = "--quantity",
                        Modifier.size(23.dp),
                        tint = Color(90, 90, 90)
                    )
                }
                Text (
                    text = "${book.quantity}",
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                    color = Color(30, 30, 30),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                IconButton (
                    onClick = { increaseQuantity(booksViewModel, userViewModel, book, navController) }
                ) {
                    Icon (
                        Icons.Rounded.AddCircle,
                        contentDescription = "++quantity",
                        Modifier.size(23.dp),
                        tint = Color(90, 90, 90)
                    )
                }
                IconButton (
                    onClick = { deleteBookFromCart(booksViewModel, userViewModel, cartItems, book, navController) },
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Icon (
                        Icons.Rounded.Delete,
                        contentDescription = "Delete book from cart",
                        Modifier.size(26.dp),
                        tint = Color(40, 40, 40)
                    )
                }
            }
        }
    }
}

fun decreaseQuantity (
    booksViewModel: BooksViewModel,
    userViewModel: UserViewModel,
    book: CartBook,
    navController: NavController
) {
    val database = booksViewModel.database
    val user = userViewModel.currentUser

    database.collection("cart")
        .document(user.userId + book.bookId)
        .update(
            mapOf(
                "quantity" to book.quantity.minus(1)
            )
        )
        .addOnSuccessListener {
            book.quantity -= 1
            navController.navigate("cart")
            navController.popBackStack()
        }
}
fun increaseQuantity(
    booksViewModel: BooksViewModel,
    userViewModel: UserViewModel,
    book: CartBook,
    navController: NavController
) {
    val database = booksViewModel.database
    val user = userViewModel.currentUser

    database.collection("cart")
        .document(user.userId + book.bookId)
        .update(
            mapOf(
                "quantity" to book.quantity.plus(1)
            )
        )
        .addOnSuccessListener {
            book.quantity += 1
            navController.navigate("cart")
            navController.popBackStack()
        }
}

fun deleteBookFromCart (
    booksViewModel: BooksViewModel,
    userViewModel: UserViewModel,
    cartItems: MutableList<CartBook>,
    book: CartBook,
    navController: NavController
) {
    val database = booksViewModel.database
    val user = userViewModel.currentUser

    database.collection("cart")
        .document(user.userId + book.bookId)
        .delete()
        .addOnSuccessListener {
            cartItems.remove(book)
            navController.navigate("cart")
            navController.popBackStack()
        }
}

fun bookDetail(book: Book, bookDetail: Book, navController: NavController) {
    bookDetail.bookId = book.bookId
    bookDetail.ownerId = book.ownerId
    bookDetail.url = book.url
    bookDetail.title = book.title
    bookDetail.description = book.description
    bookDetail.author = book.author
    bookDetail.cost = book.cost
    bookDetail.buyingCount = book.buyingCount
    navController.navigate("bookDetail")
}

fun updateBook(book: Book, bookDetail: Book, navController: NavController) {
    bookDetail.bookId = book.bookId
    bookDetail.ownerId = book.ownerId
    bookDetail.url = book.url
    bookDetail.title = book.title
    bookDetail.description = book.description
    bookDetail.author = book.author
    bookDetail.cost = book.cost
    bookDetail.buyingCount = book.buyingCount
    navController.navigate("updateBook")
}