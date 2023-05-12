package com.example.bookstore.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.SellerViewModel
import com.example.bookstore.viewmodels.UserViewModel

@Composable
fun LoginScreen (
    navController: NavController,
    booksViewModel: BooksViewModel,
    userViewModel: UserViewModel,
    sellerViewModel: SellerViewModel,
    applicationContext: Context,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255, 247, 237))
            .padding(top = 35.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row (
            modifier = Modifier.padding(start = 25.dp, end = 25.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton (
                onClick = { navController.popBackStack("welcome", inclusive = false) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, "contentDescription")
            }
            Text (
                text = "Login to Account",
                fontSize = 21.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                color = Color(30, 30, 30)
            )
        }

        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 40.dp, bottom = 50.dp)
        ) {
            var emailText by remember { mutableStateOf("") }
            var passwordText by remember { mutableStateOf("") }

            Text (
                text = "BookStore",
                color = Color(128, 171, 111),
                fontSize = 40.sp,
                fontFamily = FontFamily(Font(R.font.nunitoblack)),
                modifier = Modifier.padding(bottom = 45.dp)
            )

            TextField (
                value = emailText,
                onValueChange = { newValue -> emailText = newValue },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp)
                    .fillMaxWidth()
                    .height(62.dp)
                    .clip(shape = RoundedCornerShape(22.dp))
                    .shadow(2.dp)
                    .background(Color(255, 250, 244, 255))
                    .padding(top = 4.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color(0, 0, 0),
                    backgroundColor = Color(255, 250, 244, 255),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                placeholder = {
                    Text(
                        text = "Your email",
                        color = Color(0, 0, 0, 90),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                },
                leadingIcon = { Image(painter = painterResource(id = R.drawable.email), contentDescription = null, modifier = Modifier.padding(start = 15.dp, bottom = 4.dp)) }
            )

            TextField (
                value = passwordText,
                onValueChange = { newValue -> passwordText = newValue },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                    .fillMaxWidth()
                    .height(62.dp)
                    .clip(shape = RoundedCornerShape(22.dp))
                    .shadow(2.dp)
                    .background(Color(255, 250, 244, 255))
                    .padding(top = 4.dp),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color(0, 0, 0),
                    backgroundColor = Color(255, 250, 244, 255),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                placeholder = {
                    Text(
                        text = "Your password",
                        color = Color(0, 0, 0, 90),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                },
                leadingIcon = { Image(painter = painterResource(id = R.drawable.password), contentDescription = null, modifier = Modifier.padding(start = 15.dp, bottom = 4.dp)) },
                visualTransformation = PasswordVisualTransformation()

            )

            Row (
                modifier = Modifier
                    .padding(top = 20.dp, start = 25.dp, end = 25.dp, bottom = 20.dp)
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color(250, 240, 228, 255))
            ) {}

            Button (
                onClick = { login(emailText, passwordText, navController, applicationContext, userViewModel, sellerViewModel, booksViewModel) },
                modifier = Modifier
                    .padding(start = 25.dp, end = 25.dp)
                    .fillMaxWidth()
                    .height(66.dp)
                    .clip(shape = RoundedCornerShape(24.dp)),
                colors = ButtonDefaults.buttonColors(Color(30, 30, 30))) {
                Text (
                    text = "Sign in",
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(R.font.nunitoblack)),
                    color = Color(255, 247, 237)
                )
            }
        }

        TextButton (
            onClick = { navController.navigate("registration") },
            modifier = Modifier
                .padding(bottom = 30.dp)
                .fillMaxWidth()
        ) {
            Text (
                text = "Don’t have an account? ",
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.nunitosemibold)),
                color = Color.Black
            )
            Text (
                text ="Sign up",
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.nunitoblack)),
                textDecoration = TextDecoration.Underline,
                color = Color(128, 171, 111)
            )
        }
    }
}

fun login(email: String, password: String, navController: NavController, applicationContext: Context, userViewModel : UserViewModel, sellerViewModel : SellerViewModel, booksViewModel: BooksViewModel) {
    if(email.isEmpty() || password.isEmpty()) {
        Toast.makeText(applicationContext, "Fill all the fields!", Toast.LENGTH_SHORT).show()
        return
    }
    booksViewModel.auth.signInWithEmailAndPassword(email, password)
        .addOnSuccessListener { success ->
            booksViewModel.database.collection("users")
                .whereEqualTo("userId", success.user?.uid)
                .get()
                .addOnSuccessListener { user ->
                    if(!user.isEmpty) {
                        for(i in user) {
                            userViewModel.setCurrentUser(i.get("userId").toString())
                        }
                        navController.navigate("main")
                    }
                    if(user.isEmpty) {
                        booksViewModel.database.collection("sellers")
                            .whereEqualTo("sellerId", success.user?.uid)
                            .get()
                            .addOnSuccessListener { seller ->
                                for(i in seller) {
                                    sellerViewModel.setCurrentUser(i.get("sellerId").toString())
                                }
                                navController.navigate("main")
                            }
                    }
                }
        }
        .addOnFailureListener {
            Toast.makeText(applicationContext, "Check your email or password!", Toast.LENGTH_SHORT).show()
        }
}