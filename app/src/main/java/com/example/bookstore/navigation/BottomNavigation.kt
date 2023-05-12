package com.example.bookstore.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.getValue
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bookstore.R
import com.example.bookstore.viewmodels.BooksViewModel
import com.example.bookstore.viewmodels.SellerViewModel
import com.example.bookstore.viewmodels.UserViewModel

@Composable
fun BottomNavigation (
    navController: NavController,
    userViewModel : UserViewModel,
    booksViewModel: BooksViewModel,
    sellerViewModel : SellerViewModel,
    signOut : ()->Unit
) {
    val screens = if(userViewModel.isAdmin()) listOf (
        BottomNavItem.Home,
        BottomNavItem.StoreList,
        BottomNavItem.Logout
    ) else if(sellerViewModel.currentSeller.sellerId.isNotEmpty()) listOf (
        BottomNavItem.Home,
        BottomNavItem.StoreList,
        BottomNavItem.AddBook,
        BottomNavItem.Profile
    ) else listOf (
        BottomNavItem.Home,
        BottomNavItem.StoreList,
        BottomNavItem.Cart,
        BottomNavItem.Profile
    )

    BottomNavigation (
        backgroundColor = Color(128, 171, 111),
        contentColor = Color(255, 255, 255, 250),
        modifier = Modifier
            .height(70.dp)
            .clip(shape = RoundedCornerShape(topStart = 21.dp, topEnd = 21.dp))
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->
            BottomNavigationItem (
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(bottom = 5.dp),
                icon = {
                    Icon (
                        painterResource(id = screen.icon),
                        contentDescription = screen.title,
                        modifier = Modifier.size(27.dp)
                    )
                },
                label = {
                    Text (
                        text = screen.title,
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.nunitoblack)),
                    )
                },
                selectedContentColor = Color(255, 255, 255),
                unselectedContentColor = Color(255, 255, 255, 180),
                alwaysShowLabel = true,
                selected = currentRoute == screen.route,
                onClick = {
                    if(screen.route == "logout") {
                        signOut()
                    } else {
                        navController.navigate(screen.route) {
                            navController.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true

                        }
                    }
                }
            )
        }
    }
}
