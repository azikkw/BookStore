package com.example.bookstore.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.viewmodels.BooksViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegistrationForStoresScreen(
    navController: NavController,
    booksViewModel : BooksViewModel,
    applicationContext: Context
) {
    val userImage = painterResource(id = R.drawable.user)
    val emailImage = painterResource(id = R.drawable.email)
    val passwordImage = painterResource(id = R.drawable.password)

    var nameText by remember { mutableStateOf("") }
    var phoneText by remember { mutableStateOf("") }
    var addressText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255, 247, 237))
            .padding(top = 30.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {navController.popBackStack("welcome", inclusive = false) },
                modifier = Modifier.size(28.dp)
            ) {
                Icon(Icons.Filled.ArrowBack, "contentDescription")
            }
            Text(
                text = "Fill Your Profile",
                fontSize = 21.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                color = Color(30, 30, 30)
            )
        }

        Column(modifier = Modifier.padding(top = 25.dp, start = 25.dp, end = 25.dp)) {
            Text (
                text = "Store name",
                color = Color(0, 0, 0),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                modifier = Modifier.padding(bottom = 15.dp)
            )
            TextField (
                value = nameText,
                onValueChange = { newValue -> nameText = newValue },
                modifier = Modifier
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
                    Text (
                        text = "Store name",
                        color = Color(0, 0, 0, 90),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                },
                leadingIcon = { Image(painter = userImage, contentDescription = null, modifier = Modifier.padding(start = 15.dp, bottom = 4.dp)) }
            )
        }
        Column(modifier = Modifier.padding(top = 15.dp, start = 25.dp, end = 25.dp)) {
            Text (
                text = "Address",
                color = Color(0, 0, 0),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                modifier = Modifier.padding(bottom = 15.dp)
            )
            TextField (
                value = addressText,
                onValueChange = { newValue -> addressText = newValue },
                modifier = Modifier
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
                    Text (
                        text = "Street address, city",
                        color = Color(0, 0, 0, 90),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                },
                leadingIcon = { Image(painter = userImage, contentDescription = null, modifier = Modifier.padding(start = 15.dp, bottom = 4.dp)) }
            )
        }
        Column(modifier = Modifier.padding(top = 15.dp, start = 25.dp, end = 25.dp)) {
            Text (
                text = "Phone",
                color = Color(0, 0, 0),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                modifier = Modifier.padding(bottom = 15.dp)
            )
            TextField (
                value = phoneText,
                onValueChange = { newValue -> phoneText = newValue },
                modifier = Modifier
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
                    Text (
                        text = "Phone number",
                        color = Color(0, 0, 0, 90),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                },
                leadingIcon = { Image(painter = userImage, contentDescription = null, modifier = Modifier.padding(start = 15.dp, bottom = 4.dp)) }
            )
        }
        Column(modifier = Modifier.padding(top = 15.dp, start = 25.dp, end = 25.dp)) {
            Text (
                text = "Email",
                color = Color(0, 0, 0),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                modifier = Modifier.padding(bottom = 15.dp)
            )
            TextField (
                value = emailText,
                onValueChange = { newValue -> emailText = newValue },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
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
                    Text (
                        text = "Your email",
                        color = Color(0, 0, 0, 90),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                },
                leadingIcon = { Image(painter = emailImage, contentDescription = null, modifier = Modifier.padding(start = 15.dp, bottom = 4.dp)) }
            )
        }
        Column(modifier = Modifier.padding(top = 15.dp, start = 25.dp, end = 25.dp)) {
            Text (
                text = "Password",
                color = Color(0, 0, 0),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold)),
                modifier = Modifier.padding(bottom = 15.dp)
            )
            TextField (
                value = passwordText,
                onValueChange = { newValue -> passwordText = newValue },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
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
                    Text (
                        text = "Your password",
                        color = Color(0, 0, 0, 90),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                },
                leadingIcon = { Image(painter = passwordImage, contentDescription = null, modifier = Modifier.padding(start = 15.dp, bottom = 4.dp)) },
                visualTransformation = PasswordVisualTransformation()
            )
        }

        Button (
            onClick = { registerStore(nameText, phoneText, addressText, emailText, passwordText, booksViewModel, navController, applicationContext) },
            modifier = Modifier
                .padding(top = 30.dp, start = 25.dp, end = 25.dp, bottom = 8.dp)
                .fillMaxWidth()
                .height(66.dp)
                .clip(shape = RoundedCornerShape(24.dp)),
            colors = ButtonDefaults.buttonColors(Color(30, 30, 30))) {
            Text (
                text = "Sign up",
                fontSize = 19.sp,
                fontFamily = FontFamily(Font(R.font.nunitoblack)),
                color = Color(255, 247, 237)
            )
        }
        TextButton(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text (
                text = "Already have an account? ",
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.nunitosemibold)),
                color = Color.Black
            )
            Text (
                text ="Sign in",
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.nunitoblack)),
                textDecoration = TextDecoration.Underline,
                color = Color(128, 171, 111)
            )
        }
    }
}

fun registerStore (
    nameText: String,
    phoneText: String,
    addressText : String,
    email: String,
    password: String,
    booksViewModel : BooksViewModel,
    navController: NavController,
    applicationContext: Context
) {
    if(nameText.isEmpty() || phoneText.isEmpty() || addressText.isEmpty() || email.isEmpty() || password.isEmpty()) {
        Toast.makeText(applicationContext, "Fill all the fields!", Toast.LENGTH_SHORT).show()
        return
    }
    else if(password.length < 8) {
        Toast.makeText(applicationContext, "The password must be more than 8 characters long!", Toast.LENGTH_SHORT).show()
        return
    }
    booksViewModel.auth.createUserWithEmailAndPassword(email, password)
        .addOnSuccessListener { success ->
            val user = hashMapOf (
                "sellerId" to success.user?.uid,
                "name" to nameText,
                "phone" to phoneText,
                "address" to addressText,
                "email" to email
            )
            success.user?.uid?.let {
                booksViewModel.database.collection("sellers")
                    .document(it)
                    .set(user)
            }
            navController.navigate("login")
        }
}