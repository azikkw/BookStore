package com.example.bookstore.data

data class Comment (
    var user_id : String,
    var firstName : String,
    var lastName : String,
    var book_id : String,
    var content : String,
    var comment_date : String
){
    constructor(): this( "", "","","" , "", "")
}