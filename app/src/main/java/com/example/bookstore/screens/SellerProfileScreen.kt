package com.example.bookstore.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.bookstore.R
import com.example.bookstore.data.Book
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.SellerViewModel
import org.w3c.dom.Text

@Composable
fun SellerProfileScreen (
    navController: NavController,
    booksViewModel: BooksViewModel,
    sellerViewModel: SellerViewModel,
    books : MutableList<Book>,
    signOut : ()->Unit,
) {
    val isProfilePage = navController.currentBackStackEntry?.destination?.route == "profile"
    val storeBooks = books.filter { book -> book.ownerId == sellerViewModel.sellerDetail.sellerId }
    if(isProfilePage){
        sellerViewModel.sellerDetail = sellerViewModel.currentSeller
    }

    Row (
        modifier = Modifier
            .height(130.dp)
            .fillMaxWidth()
            .zIndex(3f)
            .offset(0.dp, 30.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Box (
            modifier = Modifier.size(110.dp)
                .background(Color.White, shape = CircleShape),
        ){
            Icon (
                painterResource(id = R.drawable.ic_round_store_24),
                contentDescription = "Store",
                Modifier.padding(5.dp).size(100.dp),
                tint = Color(40, 40, 40),
            )
        }
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(128, 171, 111)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(2f)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(!isProfilePage) {
                    IconButton (
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            Icons.Filled.ArrowBack,
                            "contentDescription",
                            Modifier.size(30.dp),
                            Color.White
                        )
                    }
                }
                if(isProfilePage){
                    IconButton(
                        onClick = { signOut() }
                    ) {
                        Icon(
                        Icons.Filled.ExitToApp,
                        "contentDescription",
                        Modifier.size(30.dp),
                        Color.White
                        )
                    }
                }
            }

        Column(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp))
                .background(Color.White)
                .padding(10.dp)
                .padding(top = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Text(
                text = sellerViewModel.sellerDetail.name,
                fontWeight = FontWeight(700),
                color = Color.Black,
                fontSize = 27.sp
            )

            Row(
                modifier = Modifier
                    .padding(top = 15.dp, bottom = 15.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Row(
                    modifier = Modifier
                        .background(color = Color(241, 249, 238, 255), RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                        
                ) {
                    Image(painter = painterResource(id = R.drawable.baseline_location_on_24),
                        contentDescription = "Location",
                        modifier = Modifier.padding(end = 5.dp)
                    )
                    Text(
                        text = sellerViewModel.sellerDetail.address,
                        color = Color(128,171,111),
                        fontWeight = FontWeight(700),
                        fontSize = 15.sp
                    )
                }

                Row(
                    modifier = Modifier
                        .background(color = Color(241, 249, 238, 255), RoundedCornerShape(8.dp))
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(painter = painterResource(id = R.drawable.baseline_local_phone_24),
                        contentDescription = "Phone",
                        modifier = Modifier.padding(end = 5.dp)
                    )
                    Text(
                        text = sellerViewModel.sellerDetail.phone,
                        color = Color(128,171,111),
                        fontWeight = FontWeight(700),
                        fontSize = 15.sp
                    )
                }
            }


            LazyColumn(modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 40.dp)) {
                items(storeBooks) { book ->
                    BookItem (
                        navController = navController,
                        booksViewModel = booksViewModel,
                        book = book,
                    )
                }
            }
        }
    }
}