package com.example.bookstore.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.data.User
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.SellerViewModel
import com.example.bookstore.viewmodels.UserViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

@Composable
fun AddBookScreen (
    navController: NavController,
    booksViewModel : BooksViewModel,
    sellerViewModel: SellerViewModel,
    applicationContext: Context
) {
    var titleText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }
    var authorText by remember { mutableStateOf("") }
    var costText by remember { mutableStateOf("") }

    var books = booksViewModel.books

    Column (
        modifier = Modifier
            .padding(bottom = 40.dp)
            .fillMaxSize()
            .background(Color(250, 250, 250))
            .padding(top = 35.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row (
            modifier = Modifier.padding(start = 20.dp, end = 25.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton (
                onClick = { navController.popBackStack("home", inclusive = false) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, "contentDescription")
            }
            Text (
                text = "Add new Book",
                fontSize = 21.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                color = Color(30, 30, 30)
            )
        }
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
                    .height(60.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .shadow(2.dp)
                    .background(Color(247, 247, 247, 255))
                    .padding(top = 2.dp, start = 5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color(0, 0, 0),
                    backgroundColor = Color(247, 247, 247),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                placeholder = {
                    Text (
                        text = "Title of book",
                        color = Color(0, 0, 0, 90),
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                },
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
                    .height(180.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .shadow(2.dp)
                    .background(Color(247, 247, 247, 255))
                    .padding(top = 2.dp, start = 5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color(0, 0, 0),
                    backgroundColor = Color(247, 247, 247),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                placeholder = {
                    Text (
                        text = "Description of book",
                        color = Color(0, 0, 0, 90),
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                },
            )
        }
        Column(modifier = Modifier.padding(top = 20.dp, start = 25.dp, end = 25.dp)) {
            Text (
                text = "Book author",
                color = Color(0, 0, 0),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                modifier = Modifier.padding(bottom = 15.dp)
            )
            TextField (
                value = authorText,
                onValueChange = { newValue -> authorText = newValue },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .shadow(2.dp)
                    .background(Color(247, 247, 247, 255))
                    .padding(top = 2.dp, start = 5.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color(0, 0, 0),
                    backgroundColor = Color(247, 247, 247),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                placeholder = {
                    Text (
                        text = "Author of book",
                        color = Color(0, 0, 0, 90),
                        fontSize = 17.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                },
            )
            Column(modifier = Modifier.padding(top = 20.dp, start = 25.dp, end = 25.dp)) {
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
                        .height(60.dp)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .shadow(2.dp)
                        .background(Color(247, 247, 247, 255))
                        .padding(top = 2.dp, start = 5.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color(0, 0, 0),
                        backgroundColor = Color(247, 247, 247),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    placeholder = {
                        Text(
                            text = "Cost of book",
                            color = Color(0, 0, 0, 90),
                            fontSize = 17.sp,
                            fontFamily = FontFamily(Font(R.font.nunitosemibold))
                        )
                    },
                )
            }
        }
        PickImageFromGallery(titleText, descriptionText, authorText, costText, navController, books, applicationContext, sellerViewModel)
    }
}

@Composable
fun PickImageFromGallery(
    title: String,
    description: String,
    author: String,
    cost: String,
    navController: NavController,
    books: MutableList<Book>,
    applicationContext: Context,
    sellerViewModel : SellerViewModel
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    Button (
        onClick = { launcher.launch("image/*") },
        modifier = Modifier
            .padding(top = 35.dp, start = 25.dp, end = 25.dp)
            .fillMaxWidth()
            .height(66.dp)
            .clip(shape = RoundedCornerShape(24.dp)),
        colors = ButtonDefaults.buttonColors(Color(128, 171, 111))
    ) {
        Text (
            text = "Upload image of book",
            color = Color(30, 30, 30),
            fontSize = 17.sp,
            fontFamily = FontFamily(Font(R.font.nunitosemibold))
        )
    }

    Column {
        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }
            bitmap.value?.let { btm ->
                Image (
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp, start = 25.dp, end = 25.dp)
                )
            }
        }
    }

    Button (
        onClick = {
//            if(imageUri == null) {
//                Toast.makeText(applicationContext, "Fill all the fields!", Toast.LENGTH_SHORT).show()
//            }
            imageUri?.let {
                addBook(title, description, author, cost, it, books, navController, applicationContext, sellerViewModel)
                println("works")
            }
        },
        modifier = Modifier
            .padding(top = 15.dp, start = 25.dp, end = 25.dp, bottom = 40.dp)
            .fillMaxWidth()
            .height(66.dp)
            .clip(shape = RoundedCornerShape(24.dp)),
        colors = ButtonDefaults.buttonColors(Color(30, 30, 30))
    ) {
        Text (
            text = "Add book",
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.nunitoblack))
        )
    }
}

fun addBook (
    title: String,
    description: String,
    author: String,
    cost: String,
    imageUri: Uri,
    books: MutableList<Book>,
    navController: NavController,
    applicationContext: Context,
    sellerViewModel : SellerViewModel
) {
    println("works2")
    val bookId = System.currentTimeMillis().toString()
    if(title.isEmpty() || description.isEmpty() || cost.isEmpty() || author.isEmpty() || imageUri.toString().isEmpty()) {
        Toast.makeText(applicationContext, "Fill all the fields!", Toast.LENGTH_SHORT).show()
    }
    else {
        println("works3")
        FirebaseStorage.getInstance().getReference("images").child(bookId)
            .putFile(imageUri)
            .addOnSuccessListener { success ->
                success.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener {
                        val book = hashMapOf(
                            "bookId" to bookId,
                            "ownerId" to sellerViewModel.currentSeller.sellerId,
                            "url" to it.toString(),
                            "title" to title,
                            "description" to description,
                            "author" to author,
                            "cost" to cost.toDouble(),
                            "buyingCount" to 0
                        )
                        Firebase.firestore.collection("books")
                            .document(bookId)
                            .set(book)
                            .addOnSuccessListener {
                                books.add(
                                    Book(
                                        bookId,
                                        sellerViewModel.currentSeller.sellerId,
                                        book["url"].toString(),
                                        title,
                                        description,
                                        author,
                                        cost.toDouble(),
                                        0
                                    )
                                )
                                navController.popBackStack("home", inclusive = false)
                            }
                    }
            }
    }
}