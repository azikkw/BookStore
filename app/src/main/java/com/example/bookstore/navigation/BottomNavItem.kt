package com.example.bookstore.navigation

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.bookstore.R

sealed class BottomNavItem(
    var title: String,
    @DrawableRes var icon: Int,
    var route: String
) {
    object Home: BottomNavItem("Home", R.drawable.ic_round_home_24, "home")
    object Cart: BottomNavItem("Cart", R.drawable.ic_round_shopping_cart_24, "cart")
    object Profile: BottomNavItem("Profile", R.drawable.ic_baseline_person_24, "profile")
    object AddBook: BottomNavItem("AddBook", R.drawable.icon_add, "Add-book")
    object StoreList: BottomNavItem("Stores", R.drawable.ic_round_store_24, "stores")
    object Logout: BottomNavItem("Logout", R.drawable.icon_logout, "logout")
}
