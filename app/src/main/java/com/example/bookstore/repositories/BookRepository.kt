package com.example.bookstore.repositories

import com.example.bookstore.data.Book
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository
@Inject
constructor (
    private val bookList: FirebaseFirestore
) {
    suspend fun getBookList(): List<Book> {
        return bookList.collection("books").get().await().map { book ->
            book.toObject(Book::class.java)
        }
    }
}