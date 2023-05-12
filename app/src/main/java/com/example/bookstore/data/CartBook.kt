package com.example.bookstore.data

data class CartBook (
    override var bookId: String,
    override var ownerId: String,
    override var url: String,
    override var title: String,
    override var description: String,
    override var author: String,
    override var cost: Double,
    override var buyingCount: Int,
    var quantity: Int,
    var addedUserId: String
) : Book (
    bookId = bookId,
    ownerId = ownerId,
    url = url,
    title = title,
    description = description,
    author = author,
    cost = cost,
    buyingCount = buyingCount
) {
    constructor(): this("", "", "", "", "", "", 0.0, 0, 1, "")
}