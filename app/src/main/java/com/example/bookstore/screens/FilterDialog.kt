package com.example.bookstore.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.RangeSlider
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.bookstore.R
import com.example.bookstore.viewmodels.BooksViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterDialog(
    filterDialog : ()->Unit,
    booksViewModel : BooksViewModel
) {

    var minPrice = 0
    var maxPrice = 10000
    var priceValues by remember { mutableStateOf(minPrice.toFloat()..maxPrice.toFloat()) }
    var bookRate by remember { mutableStateOf(0) }
    var selectedSortMethod by remember { mutableStateOf("") }
    var sortMethods by remember {
        mutableStateOf(listOf("First cheaper", "First more expensive"))
    }

    Dialog(onDismissRequest = { filterDialog() }) {
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = Color.White
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .height(300.dp)
                    .width(300.dp)
                    .padding(40.dp)
            ) {
                Column() {
                    Text(
                        text = "Price: ${priceValues.start.toInt()} - ${priceValues.endInclusive.toInt()}",
                        fontFamily = FontFamily(Font(R.font.nunitobold)),
                        color = Color(30, 30, 30)
                    )

                    RangeSlider(
                        value = priceValues,
                        onValueChange = { priceValues = it },
                        onValueChangeFinished = {
                            // this is called when the user completed selecting the value
                        },
                        valueRange = minPrice.toFloat()..maxPrice.toFloat(),
                        colors = SliderDefaults.colors(
                            thumbColor = Color(128, 171, 111),
                            activeTrackColor = Color(128, 171, 111)
                        )
                    )
                }

                Column() {
                    Text(
                        text = "Rating",
                        fontFamily = FontFamily(Font(R.font.nunitobold)),
                        color = Color(30, 30, 30)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth().height(40.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(1.dp),
                            modifier = Modifier.width(180.dp)
                        ) {
                            items(bookRate) {
                                IconButton(
                                    onClick = { bookRate = (it + 1) },
                                    modifier = Modifier.size(33.dp),
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.icon_star_filled),
                                        contentDescription = "Star",
                                        modifier = Modifier.size(33.dp),
                                        tint = Color(255, 153, 0)
                                    )
                                }
                            }
                            items(5 - bookRate) {
                                IconButton(
                                    onClick = { bookRate = (it + bookRate + 1) },
                                    modifier = Modifier.size(33.dp),
                                ) {
                                    Icon(
                                        painterResource(id = R.drawable.icon_star_outlined),
                                        contentDescription = "Star",
                                        modifier = Modifier.size(33.dp),
                                        tint = Color(255, 153, 0)
                                    )
                                }
                            }
                        }
                        IconButton (
                            onClick = { bookRate = 0 }
                        ) {
                            Icon (
                                painterResource(id = R.drawable.icon_cancel),
                                contentDescription = "Cancel",
                                Modifier.fillMaxHeight().width(25.dp),
                            )
                        }
                    }

                    Button(
                        onClick = {
                            booksViewModel.isFilter = false
                            booksViewModel.isFiltered()
                            booksViewModel.minPrice = minPrice
                            booksViewModel.maxPrice = maxPrice
                            booksViewModel.priceValues = priceValues
                            booksViewModel.getAllBookByRate(bookRate)
                            filterDialog()
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(128, 171, 111),
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Apply filters",
                            fontFamily = FontFamily(Font(R.font.nunitobold))
                        )
                    }
                }
            }
        }
    }
}