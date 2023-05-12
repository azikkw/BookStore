package com.example.bookstore.data

data class User (
    var userType : String,
    var userId: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var balance: Double
) {
    constructor(): this( "","", "", "" , "" , 0.0)
}