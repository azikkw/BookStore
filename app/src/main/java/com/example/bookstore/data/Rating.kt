package com.example.bookstore.data

data class Rating (
    val user_id : String,
    val book_id : String,
    val rate : Int
) {
    constructor(): this("","",0)
}