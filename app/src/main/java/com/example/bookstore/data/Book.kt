package com.example.bookstore.data

open class Book (
    open var bookId: String,
    open var ownerId : String,
    open var url: String,
    open var title: String,
    open var description: String,
    open var author: String,
    open var cost: Double,
    open var buyingCount: Int,
) {
    constructor(): this("", "", "", "", "", "", 0.0, 0)
}
