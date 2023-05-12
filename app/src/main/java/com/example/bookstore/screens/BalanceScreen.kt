package com.example.bookstore.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.data.User
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.UserViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

@Composable
fun BalanceScreen (
    navController: NavController,
    userViewModel: UserViewModel,
    context: Context
) {
    val user = userViewModel.currentUser
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(250, 250, 250))
    ) {
        // Top bar
        Surface (
            modifier = Modifier.padding(bottom = 15.dp),
            elevation = 9.dp
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(255, 255, 255))
                    .padding(top = 15.dp, bottom = 15.dp, start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text (
                    text = "Balance",
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(R.font.quicksandbold)),
                    color = Color(30, 30, 30)
                )
                // Cart items total cost
                Text (
                    text = "${user.balance}₸",
                    fontSize = 17.sp,
                    fontFamily = FontFamily(Font(R.font.quicksandsemibold)),
                    color = Color(129, 129, 129, 255)
                )
            }
        }

        // Return back
        IconButton (
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(start = 15.dp)
                .size(28.dp)
        ) {
            Icon(Icons.Filled.ArrowBack, "contentDescription", tint = Color.Black)
        }

        // Fill card data
        CardNumberTextField(navController, context, user)
    }
}

@Composable
fun CardNumberTextField (
    navController: NavController,
    context: Context,
    user: User,
) {
    var sum by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var dateMonth by remember { mutableStateOf("") }
    var dateYear by remember { mutableStateOf("") }
    var cardCsv by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .padding(top = 25.dp, start = 20.dp, end = 20.dp, bottom = 12.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(12.dp))
            .background(Color(128, 171, 111))
            .padding(top = 25.dp, start = 15.dp, end = 15.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = cardNumber,
            onValueChange = { if(it.length <= 16) cardNumber = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = VisualTransformation { number ->
                formatCardNumbers(number)
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .width(272.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            placeholder = {
                Text(
                    text = "#### #### #### ####",
                    color = Color(200, 200, 200),
                    fontSize = 23.sp,
                    fontFamily = FontFamily(Font(R.font.nunitosemibold))
                )
            },
            textStyle = TextStyle.Default.copy(
                fontSize = 23.sp,
                fontFamily = FontFamily(Font(R.font.nunitoextrabold))
            )
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = dateMonth,
                    onValueChange = { if (it.length <= 2) dateMonth = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .width(60.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    placeholder = {
                        Text(
                            text = "00",
                            color = Color(200, 200, 200),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunitosemibold))
                        )
                    },
                    textStyle = TextStyle.Default.copy(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                )
                Text(
                    text = "/",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.nunitosemibold)),
                    color = Color.White,
                    modifier = Modifier.offset(x = (-17).dp, y = (-2).dp)
                )
                TextField(
                    value = dateYear,
                    onValueChange = { if (it.length <= 2) dateYear = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    modifier = Modifier
                        .width(57.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .offset(x = (-26).dp),
                    placeholder = {
                        Text(
                            text = "00",
                            color = Color(200, 200, 200),
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.nunitosemibold))
                        )
                    },
                    textStyle = TextStyle.Default.copy(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                )
            }
            TextField(
                value = cardCsv,
                onValueChange = { if (it.length <= 3) cardCsv = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier
                    .width(62.dp)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                placeholder = {
                    Text(
                        text = "000",
                        color = Color(200, 200, 200),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.nunitosemibold))
                    )
                },
                textStyle = TextStyle.Default.copy(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.nunitosemibold))
                )
            )
        }
    }

    TextField (
        value = sum,
        onValueChange = { newValue -> sum = newValue },
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(15.dp))
            .background(Color(240, 240, 240, 255))
            .padding(start = 5.dp, end = 5.dp),
        colors = TextFieldDefaults.textFieldColors (
            textColor = Color(0, 0, 0),
            backgroundColor = Color(240, 240, 240, 255),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = "Enter the desired amount",
                color = Color(0, 0, 0, 90),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.nunitosemibold))
            )
        },
        trailingIcon = {
            Icon (
                painterResource(id = R.drawable.ic_round_account_balance_wallet_24),
                contentDescription = "Balance",
                Modifier.size(25.dp),
                tint = Color.Black
            )
        },
    )

    // Replenish balance button
    Button (
        onClick = {
            replenishBalance(user, sum, cardNumber, dateMonth, dateYear, cardCsv, navController, context)
            sum = ""; cardNumber = ""; dateMonth = ""; dateYear = ""; cardCsv = ""
        },
        modifier = Modifier
            .padding(top = 40.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .height(65.dp)
            .clip(shape = RoundedCornerShape(15.dp)),
        colors = ButtonDefaults.buttonColors(Color(30, 30, 30))) {
        Text (
            text = "Replenish",
            fontSize = 19.sp,
            fontFamily = FontFamily(Font(R.font.nunitoblack)),
            color = Color.White
        )
    }
}
fun formatCardNumbers(text: AnnotatedString): TransformedText {
    val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
    var out = ""

    for(i in trimmed.indices) {
        out += trimmed[i]
        if(i % 4 == 3 && i != 15) out += " "
    }
    val creditCardOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if(offset <= 3) return offset
            if(offset <= 7) return offset + 1
            if(offset <= 11) return offset + 2
            if(offset <= 16) return offset + 3
            return 19
        }
        override fun transformedToOriginal(offset: Int): Int {
            if(offset <= 4) return offset
            if(offset <= 9) return offset - 1
            if(offset <= 14) return offset - 2
            if(offset <= 19) return offset - 3
            return 16
        }
    }

    return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
}

fun replenishBalance (
    user: User,
    sum: String,
    cardNumber: String,
    dateMonth: String,
    dateYear: String,
    cardCsv: String,
    navController: NavController,
    context: Context
) {
    if(cardNumber.length < 16 || dateMonth.length < 2 || dateYear.length < 2 || cardCsv.length < 2) {
        Toast.makeText(context, "Fill out your card!", Toast.LENGTH_SHORT).show()
        return
    }
    else if(sum.length < 3) Toast.makeText(context, "You need to top up the card for at least 100₸", Toast.LENGTH_SHORT).show()
    else {
        Firebase.firestore.collection("users")
            .document(user.userId)
            .update(
                mapOf(
                    "balance" to user.balance.plus(sum.toDouble())
                )
            )
            .addOnSuccessListener {
                Toast.makeText(context, "Your balance received $sum₸", Toast.LENGTH_SHORT).show()
                user.balance += sum.toDouble()
                navController.navigate("balance")
                navController.popBackStack()
            }
    }
}