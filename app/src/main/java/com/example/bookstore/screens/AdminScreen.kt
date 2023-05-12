package com.example.bookstore.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.data.User

@Composable
fun AdminScreen(navController: NavController, user: User) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp)
    ) {
        Row (
            modifier = Modifier
                .padding(start = 14.dp, end = 25.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            var expanded by remember { mutableStateOf(false) }
            Box {
                IconButton(onClick = { expanded = true }) {
                    Icon (
                        Icons.Default.Menu,
                        contentDescription = "Show menu",
                        Modifier.size(30.dp)
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(x = 5.dp, y = 5.dp),
                    modifier = Modifier
                        .background(Color(250, 250, 250, 255))
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    DropdownMenuItem(onClick = {  }) {
                        Text (
                            text = "Add book",
                            color = Color(30, 30, 30),
                            fontFamily = FontFamily(Font(R.font.nunitosemibold)),
                        )
                    }
                    Divider(color = Color(230, 230, 230, 255))
                    DropdownMenuItem(onClick = { }) {
                        Text (
                            text = "Exit",
                            color = Color(30, 30, 30),
                            fontFamily = FontFamily(Font(R.font.nunitosemibold)),
                        )
                    }
                }
            }
            IconButton (
                onClick = { },
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
            text = "Book Store",
            color = Color(30, 30, 30),
            fontSize = 40.sp,
            fontFamily = FontFamily(Font(R.font.nunitoblack)),
            modifier = Modifier.padding(top = 40.dp, start = 25.dp, end = 25.dp, bottom = 15.dp)
        )

        Row (
            modifier = Modifier
                .padding(start = 25.dp, end = 5.dp)
                .fillMaxWidth()
                .height(27.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text (
                text = "List of books",
                color = Color(172, 172, 172, 255),
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.nunitobold))
            )
            Row {
                IconButton(onClick = {  }) {
                    Icon (
                        Icons.Default.Add,
                        contentDescription = "Add book",
                        Modifier.size(27.dp),
                        tint = Color(60, 60, 60, 255)
                    )
                }
                IconButton(onClick = { }) {
                    Icon (
                        Icons.Default.MoreVert,
                        contentDescription = "Sort by",
                        Modifier.size(26.dp),
                        tint = Color(60, 60, 60, 255)
                    )
                }
            }
        }
    }
}