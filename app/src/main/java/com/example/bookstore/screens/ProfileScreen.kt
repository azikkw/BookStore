package com.example.bookstore.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.data.User
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.UserViewModel

@Composable
fun ProfileScreen (
    navController: NavController,
    userViewModel : UserViewModel,
    signOut : () -> Unit
) {
    val user = userViewModel.currentUser

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 25.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row (
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton (
                onClick = { navController.navigate("balance") },
                modifier = Modifier.offset(x = (-12).dp)
            ) {
                Icon (
                    painterResource(id = R.drawable.ic_round_account_balance_wallet_24),
                    contentDescription = "Search",
                    Modifier.padding(end = 6.dp).size(26.dp),
                    tint = Color.Black
                )
                Text (
                    text = "${user.balance}₸",
                    fontSize = 17.sp,
                    fontFamily = FontFamily(Font(R.font.nunitosemibold)),
                    color = Color(30, 30, 30),
                    letterSpacing = 0.sp
                )
            }
            IconButton (
                onClick = { navController.navigate("search") },
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .clip(shape = RoundedCornerShape(14.dp))
                    .background(Color(128, 171, 111))
            ) {
                Icon (
                    Icons.Default.Search,
                    contentDescription = "Search",
                    Modifier.size(25.dp),
                    tint = Color.White
                )
            }
        }

        Text (
            text = "Hello " + user.firstName,
            color = Color(30, 30, 30),
            fontSize = 38.sp,
            fontFamily = FontFamily(Font(R.font.nunitoblack)),
            modifier = Modifier.padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 15.dp)
        )

        Row (
            modifier = Modifier.padding(start = 20.dp, end = 20.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text (
                text = "First name:",
                color = Color(102, 102, 102, 255),
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.nunitosemibold))
            )
            Text (
                text = user.firstName,
                color = Color(30, 30, 30),
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.nunitoblack))
            )
        }
        Divider(color = Color(230, 230, 230, 255), modifier = Modifier.padding(top = 15.dp, start = 20.dp, end = 20.dp, bottom = 15.dp))

        Row (
            modifier = Modifier.padding(start = 20.dp, end = 20.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text (
                text = "Last name:",
                color = Color(102, 102, 102, 255),
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.nunitosemibold))
            )
            Text (
                text = user.lastName,
                color = Color(30, 30, 30),
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.nunitoblack))
            )
        }
        Divider(color = Color(230, 230, 230, 255), modifier = Modifier.padding(top = 15.dp, start = 20.dp, end = 20.dp, bottom = 15.dp))

        Row (
            modifier = Modifier.padding(start = 20.dp, end = 20.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text (
                text = "Email:",
                color = Color(102, 102, 102, 255),
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.nunitosemibold))
            )
            Text (
                text = user.email,
                color = Color(30, 30, 30),
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.nunitoblack))
            )
        }
        Divider(color = Color(230, 230, 230, 255), modifier = Modifier.padding(top = 15.dp, start = 20.dp, end = 20.dp, bottom = 15.dp))

        Row (
            modifier = Modifier.padding(start = 20.dp, end = 20.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text (
                text = "Balance:",
                color = Color(102, 102, 102, 255),
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.nunitosemibold))
            )
            Text (
                text = "${user.balance}₸",
                color = Color(30, 30, 30),
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.nunitoblack))
            )
        }

        Button (
            onClick = { signOut() },
            modifier = Modifier
                .padding(top = 50.dp, start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .height(65.dp)
                .clip(shape = RoundedCornerShape(15.dp)),
            colors = ButtonDefaults.buttonColors(Color(30, 30, 30))) {
            Text (
                text = "Exit",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunitoblack)),
                color = Color.White
            )
        }
    }
}