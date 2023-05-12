package com.example.bookstore.screens

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.example.bookstore.data.CartBook
import com.example.bookstore.data.Comment
import com.example.bookstore.data.Seller
import com.example.bookstore.data.User
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.SellerViewModel
import com.example.bookstore.viewmodels.UserViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


@Composable
fun BookDetails (
    navController: NavController,
    booksViewModel: BooksViewModel,
    userViewModel: UserViewModel,
    sellerViewModel: SellerViewModel,
    applicationContext: Context,
    cartItems: MutableList<CartBook>,
    books : MutableList<Book>
) {
    val book = booksViewModel.bookDetail
    val user = userViewModel.currentUser

    val seller = sellerViewModel.sellers.filter { seller ->
        seller.sellerId == book.ownerId
    }

    var comments = booksViewModel.bookDetailComments
    var comment by remember { mutableStateOf("") }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(250, 250, 250))
    ) {
        // Page background image
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
                .padding(top = 20.dp, start = 20.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top navigation part
            Row (
                modifier = Modifier
                    .height(230.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column (
                    modifier = Modifier.height(210.dp),
                    verticalArrangement = Arrangement.SpaceBetween
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
                    if(sellerViewModel.currentSeller.sellerId.isNotEmpty() && sellerViewModel.currentSeller.sellerId == book.ownerId) {
                        Row {
                            Button (
                                onClick = {
                                    navController.navigate("update-book")
                                },
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .height(50.dp)
                                    .clip(shape = RoundedCornerShape(9.dp)),
                                colors = ButtonDefaults.buttonColors(Color(128, 171, 111))
                            ) {
                                Icon (
                                    painterResource(id = R.drawable.icon_edit),
                                    contentDescription = "Update",
                                    Modifier
                                        .padding(top = 2.dp)
                                        .size(20.dp),
                                    tint = Color.White
                                )
                            }
                            Button (
                                onClick = {
                                    deleteBook(navController, booksViewModel, books)
                                },
                                modifier = Modifier
                                    .height(50.dp)
                                    .clip(shape = RoundedCornerShape(9.dp)),
                                colors = ButtonDefaults.buttonColors(Color(128, 171, 111))
                            ) {
                                Icon (
                                    Icons.Rounded.Delete,
                                    contentDescription = "Delete",
                                    Modifier
                                        .padding(top = 2.dp)
                                        .size(20.dp),
                                    tint = Color.White
                                )
                            }
                        }
                    }
                    else if(sellerViewModel.currentSeller.sellerId.isNotEmpty() || userViewModel.isAdmin()){
                        Text (
                            text = "${book.cost.toInt()}₸",
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.quicksandbold)),
                            color = Color(255, 247, 237),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                    } else {
                        var contains = false
                        cartItems.forEach { book ->
                            if(book.addedUserId == user.userId) {
                                contains = true
                            }
                        }
                        Button (
                            onClick = {
                                if(contains) navController.navigate("cart")
                                else {
                                    addToCart (
                                        navController,
                                        booksViewModel,
                                        userViewModel,
                                        cartItems,
                                        applicationContext
                                    )
                                }
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .clip(shape = RoundedCornerShape(9.dp)),
                            colors = ButtonDefaults.buttonColors(Color(128, 171, 111))
                        ) {
                            Text (
                                text = "${book.cost.toInt()}₸",
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(R.font.quicksandbold)),
                                color = Color(255, 247, 237),
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Icon (
                                painterResource(id = if(contains) R.drawable.ic_round_shopping_cart_checkout_24 else R.drawable.ic_round_add_shopping_cart_24),
                                contentDescription = "Add to cart",
                                Modifier
                                    .padding(top = 2.dp)
                                    .size(20.dp),
                                tint = Color.White
                            )
                        }
                    }
                }
                // Poster of book part
                AsyncImage (
                    model = book.url,
                    contentDescription = "Book detail main image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(145.dp)
                        .height(210.dp)
                        .shadow(5.dp)
                )
            }

            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title of book part
                item {
                    Text (
                        text = book.title,
                        fontSize = 24.sp,
                        fontFamily = FontFamily(Font(R.font.quicksandbold)),
                        color = Color(30, 30, 30),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }

                // Author part
                item {
                    Text (
                        text = "J.K. Rowling",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.quicksandbold)),
                        color = Color(0, 0, 0, 140),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 7.dp, bottom = 22.dp)
                    )
                }

                // Book media info
                item {
                    Row (
                        modifier = Modifier
                            .padding(bottom = 10.dp)
                            .width(250.dp)
                            .height(75.dp)
                            .clip(shape = RoundedCornerShape(14.dp))
                            .background(Color(30, 30, 30)),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(end = 15.dp)
                        ) {
                            Text(
                                text = "${comments.size}",
                                fontSize = 28.sp,
                                fontFamily = FontFamily(Font(R.font.quicksandbold)),
                                color = Color(255, 255, 255),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Comments",
                                fontSize = 13.sp,
                                fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                                color = Color(255, 255, 255, 150),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.offset(x = 0.dp, y = (-5).dp)
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(start = 15.dp)
                        ) {
                            Text(
                                text = "${book.buyingCount}",
                                fontSize = 28.sp,
                                fontFamily = FontFamily(Font(R.font.quicksandbold)),
                                color = Color(255, 255, 255),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Acquisitions",
                                fontSize = 13.sp,
                                fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                                color = Color(255, 255, 255, 150),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.offset(x = 0.dp, y = (-5).dp)
                            )
                        }
                    }
                }

                // Book rating part
                item {
                    Rating("" , booksViewModel)
                }

                // Book description part
                item {
                    Text (
                        text = book.description,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                        color = Color(0, 0, 0, 140),
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(bottom = 30.dp)
                    )
                }

                if(seller.isNotEmpty() && seller[0].sellerId != book.ownerId) {
                    // Seller part
                    item {
                        SellerPart(book, seller[0], sellerViewModel, navController)
                    }
                }

                if(!userViewModel.isAdmin() && user.userId != "") {
                    // Seller part
                    item {
                        SellerPart(book, seller[0], sellerViewModel, navController)
                    }

                    // My impression part
                    item {
                        MyRating(
                            navController,
                            booksViewModel.i_Im_rated_this_book,
                            user,
                            booksViewModel,
                            userViewModel
                        )
                    }

                    // Add comment part
                    item {
                        TextField(
                            value = comment,
                            onValueChange = { newValue -> comment = newValue },
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(12.dp))
                                .background(Color(240, 240, 240))
                                .padding(start = 5.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                textColor = Color(0, 0, 0),
                                backgroundColor = Color(240, 240, 240),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            placeholder = {
                                Text(
                                    text = "Share your opinion",
                                    color = Color(0, 0, 0, 90),
                                    fontSize = 16.sp,
                                    fontFamily = FontFamily(Font(R.font.quicksandsemibold))
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        val commentItem = Comment(
                                            user_id = user.userId,
                                            firstName = user.firstName,
                                            lastName = user.lastName,
                                            book_id = booksViewModel.bookDetail.bookId,
                                            content = comment,
                                            "10.05.2023"
                                        )
                                        addComment(commentItem, booksViewModel, navController)
                                        comment = ""
                                    },
                                    modifier = Modifier
                                        .width(55.dp)
                                        .height(55.dp)
                                        .clip(shape = RoundedCornerShape(12.dp))
                                        .background(Color(30, 30, 30))
                                ) {
                                    Icon(
                                        Icons.Rounded.Send,
                                        contentDescription = "Send",
                                        Modifier.size(24.dp),
                                        tint = Color.White
                                    )
                                }
                            },
                        )
                    }
                }

                // Clients comments part
                item {
                    Text (
                        text = "Impressions",
                        fontSize = 21.sp,
                        fontFamily = FontFamily(Font(R.font.quicksandbold)),
                        color = Color(30, 30, 30),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(top = 20.dp, bottom = 15.dp)
                            .fillMaxWidth()
                    )
                }
                // Leaved comments part
                items(comments.size) { i ->
                    Comments(comments[i])
                }

                if(comment.isEmpty()) {
                    item {
                        Text (
                            text = "This book has not been commented on yet",
                            color = Color(120, 120, 120),
                            fontSize = 17.sp,
                            fontFamily = FontFamily(Font(R.font.nunitosemibold)),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(bottom = 15.dp).fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun RatingForMyRating (
    option: String,
    booksViewModel: BooksViewModel
) {
    val ratingValue = booksViewModel.myRatingInBook.rate
    LazyRow (
        modifier = Modifier.padding(bottom = 15.dp)
    ) {
        items(ratingValue) {
            Icon(
                Icons.Rounded.Star,
                contentDescription = "Search",
                Modifier.size(23.dp),
                tint = Color(255, 153, 0)
            )
        }
        items(5 - ratingValue) {
            Icon (
                painterResource(id = R.drawable.ic_round_star_border_24),
                contentDescription = "Search",
                Modifier.size(23.dp),
                tint = Color(255, 153, 0)
            )
        }
        if(option != "my-rate") {
            item {
                Text(
                    text = "(${booksViewModel.ratedPeople} people rated)",
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                    color = Color(30, 30, 30, 140),
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

@Composable
fun Rating (
    option: String,
    booksViewModel: BooksViewModel
) {
    val ratingValue = booksViewModel.bookRatings
    LazyRow (
        modifier = Modifier.padding(bottom = 15.dp)
    ) {
        items(ratingValue) {
            Icon(
                Icons.Rounded.Star,
                contentDescription = "Search",
                Modifier.size(23.dp),
                tint = Color(255, 153, 0)
            )
        }
        items(5 - ratingValue) {
            Icon (
                painterResource(id = R.drawable.ic_round_star_border_24),
                contentDescription = "Search",
                Modifier.size(23.dp),
                tint = Color(255, 153, 0)
            )
        }
        if(option != "my-rate") {
            item {
                Text(
                    text = "(${booksViewModel.ratedPeople} people rated)",
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                    color = Color(30, 30, 30, 140),
                    modifier = Modifier.padding(start = 5.dp)
                )
            }
        }
    }
}

@Composable
fun SellerPart (
    book: Book,
    seller: Seller,
    sellerViewModel: SellerViewModel,
    navController : NavController
) {
    Text (
        text = "From",
        fontSize = 21.sp,
        fontFamily = FontFamily(Font(R.font.quicksandbold)),
        color = Color(30, 30, 30),
        textAlign = TextAlign.Start,
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
    )
    Row (
        modifier = Modifier
            .padding(bottom = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button (
            onClick = { storeDetail(seller, sellerViewModel, navController) },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .padding(end = 12.dp)
                .width(52.dp)
                .height(52.dp)
                .clip(RoundedCornerShape(10.dp)),
            colors = ButtonDefaults.buttonColors(Color(128, 171, 111))
        ) {
            Icon (
                painterResource(id = R.drawable.ic_round_store_24),
                contentDescription = "Store",
                Modifier.size(30.dp),
                tint = Color(255, 255, 255)
            )
        }
        Column (
            modifier = Modifier.padding(bottom = 5.dp)
        ) {
            Text (
                text = seller.name,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.quicksandbold)),
                color = Color(30, 30, 30)
            )
            Text (
                text = "Seller",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                color = Color(90, 90, 90)
            )
        }
    }
}

@Composable
fun MyRating (
    navController: NavController,
    option: Boolean,
    user: User,
    booksViewModel: BooksViewModel,
    userViewModel : UserViewModel
) {
    if(option) {
        Box {
            Column {
                Text (
                    text = "My rating",
                    fontSize = 21.sp,
                    fontFamily = FontFamily(Font(R.font.quicksandbold)),
                    color = Color(30, 30, 30),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxWidth()
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon (
                        Icons.Rounded.AccountCircle,
                        contentDescription = "Back",
                        Modifier
                            .padding(end = 7.dp, bottom = 12.dp)
                            .size(42.dp),
                        tint = Color(30, 30, 30, 220)
                    )
                    Column {
                        Text (
                            text = "${user.firstName} ${user.lastName}",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.quicksandbold)),
                            color = Color(30, 30, 30, 220)
                        )
                        if(option){
                            RatingForMyRating ("my-rate",booksViewModel = booksViewModel)
                        }
                        else{
                            Rating("my-rate" , booksViewModel = booksViewModel)

                        }
                    }
                }
            }
            IconButton (
                onClick = { deleteMyRate(navController, booksViewModel, userViewModel) },
                modifier = Modifier
                    .offset(x = 12.dp, y = 38.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon (
                    Icons.Rounded.Delete,
                    contentDescription = "Delete my rate",
                    Modifier.size(25.dp),
                    tint = Color(30, 30, 30)
                )
            }
        }
    } else {
        Button (
            onClick = { navController.navigate("rate-book") },
            modifier = Modifier
                .fillMaxWidth()
                .height(65.dp)
                .clip(shape = RoundedCornerShape(14.dp)),
            colors = ButtonDefaults.buttonColors(Color(30, 30, 30))
        ) {
            Text (
                text = "Rate book",
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                color = Color(255, 247, 237),
                modifier = Modifier.padding(end = 5.dp)
            )
            Icon (
                Icons.Rounded.Star,
                contentDescription = "Star",
                Modifier.size(22.dp),
                tint = Color(255, 247, 237)
            )
        }
    }
}

@Composable
fun Comments (
    comment: Comment
) {
    Column (
        modifier = Modifier
            .padding(bottom = 13.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .background(Color(245, 245, 245))
            .padding(15.dp)
    ) {
        Row (
            modifier = Modifier.padding(bottom = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon (
                Icons.Rounded.AccountCircle,
                contentDescription = "Back",
                Modifier
                    .padding(end = 5.dp)
                    .size(24.dp),
                tint = Color(0, 0, 0, 150)
            )
            Text (
                text = "${comment.firstName} ${comment.lastName}",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.quicksandbold)),
                color = Color(0, 0, 0, 125)
            )
        }
        Text (
            text = comment.content,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
            color = Color(30, 30, 30)
        )
    }
}

fun addToCart (
    navController: NavController,
    booksViewModel: BooksViewModel,
    userViewModel: UserViewModel,
    cartItems: MutableList<CartBook>,
    context: Context
) {
    val database = booksViewModel.database
    val book = booksViewModel.bookDetail
    val user = userViewModel.currentUser

    val cartItem = hashMapOf (
        "bookId" to book.bookId,
        "ownerId" to book.ownerId,
        "url" to book.url,
        "title" to book.title,
        "description" to book.description,
        "author" to book.author,
        "cost" to book.cost,
        "buyingCount" to book.buyingCount,
        "quantity" to 1,
        "addedUserId" to user.userId
    )

    database.collection("cart")
        .document(user.userId + book.bookId)
        .set(cartItem)
        .addOnSuccessListener {
            Toast.makeText(context, "Book successfully added to cart!", Toast.LENGTH_SHORT).show()
            cartItems.add(
                CartBook (
                    book.bookId,
                    book.ownerId,
                    book.url,
                    book.title,
                    book.description,
                    book.author,
                    book.cost,
                    book.buyingCount,
                    1,
                    user.userId
                )
            )
            navController.navigate("bookDetail")
            navController.popBackStack()
        }
}

fun addComment (
    comment: Comment ,
    booksViewModel: BooksViewModel,
    navController: NavController
){
    val database = booksViewModel.database
    database.collection("Comments")
        .add(comment)
        .addOnSuccessListener {
            booksViewModel.bookDetailComments.add(comment)
            navController.navigate("bookDetail")
            navController.popBackStack()
        }
        .addOnFailureListener {
            println("Failed to add comment:")
        }
}

fun deleteMyRate (
    navController: NavController,
    booksViewModel: BooksViewModel,
    userViewModel: UserViewModel
) {
    booksViewModel.database.collection("ratings")
        .document(userViewModel.currentUser.userId + booksViewModel.bookDetail.bookId)
        .delete()
        .addOnSuccessListener {
            booksViewModel.i_Im_rated_this_book = false
            booksViewModel.ratedPeople--
            navController.navigate("bookDetail")
            navController.popBackStack()
        }
}

fun deleteBook (
    navController: NavController,
    booksViewModel: BooksViewModel,
    books : MutableList<Book>
) {
    val book = booksViewModel.bookDetail
    booksViewModel.database.collection("books")
        .document(book.bookId)
        .delete()
        .addOnSuccessListener {
            Log.d(ContentValues.TAG, "DocumentSnapshot successfully deleted!")
            books.remove(book)
            navController.popBackStack()
        }
        .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error deleting document", e) }
}