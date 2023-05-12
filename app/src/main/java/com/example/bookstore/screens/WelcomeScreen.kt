package com.example.bookstore.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.R
import org.w3c.dom.Text

@Composable
fun WelcomeScreen(navController: NavController) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(255, 247, 237))
            .padding(top = 30.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text (
            text = "BookStore",
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.nunitoblack))
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image (
                painter = painterResource(id = R.drawable.bookstorewelcome),
                contentDescription = null,
                modifier = Modifier
                    .width(250.dp)
                    .height(390.dp)
            )
            Column (
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 45.dp))
                    .fillMaxWidth()
                    .background(Color(252, 234, 212))
                    .padding(top = 15.dp, bottom = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button (
                    onClick = { navController.navigate("registration") },
                    modifier = Modifier
                        .padding(top = 23.dp, start = 25.dp, end = 25.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .height(66.dp)
                        .clip(shape = RoundedCornerShape(24.dp)),
                    colors = ButtonDefaults.buttonColors(Color(30, 30, 30))) {
                    Text (
                        text = "Get started",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.nunitoblack)),
                        color = Color(255, 247, 237)
                    )
                }
                Button (
                    onClick = { navController.navigate("registration-for-stores") },
                    modifier = Modifier
                        .padding(top = 5.dp, start = 25.dp, end = 25.dp, bottom = 8.dp)
                        .fillMaxWidth()
                        .height(66.dp)
                        .clip(shape = RoundedCornerShape(24.dp)),
                    colors = ButtonDefaults.buttonColors(Color(30, 30, 30))) {
                    Text (
                        text = "For stores",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.nunitoblack)),
                        color = Color(255, 247, 237)
                    )
                }
                TextButton (
                    onClick = { navController.navigate("login")},
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
    }
}