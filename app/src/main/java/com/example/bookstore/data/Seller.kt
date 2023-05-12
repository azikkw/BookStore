package com.example.bookstore.data

data class Seller(
    var sellerId: String,
    var name: String,
    var email: String,
    var phone : String,
    var address : String
){
    constructor(): this("", "", "", "", "")
}