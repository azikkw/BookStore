package com.example.bookstore.viewmodels

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.data.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@SuppressLint("MutableCollectionMutableState")
@HiltViewModel
class BooksViewModel
@Inject
constructor(): ViewModel() {

    var books by mutableStateOf<MutableList<Book>>(mutableListOf())

    var bookDetail by mutableStateOf(Book("", "", "", "", "", "", 0.0, 0))

    val auth: FirebaseAuth = Firebase.auth
    val database: FirebaseFirestore = Firebase.firestore
    val user = User("", "","",  "", "", 0.0)
    var userCart by mutableStateOf<MutableList<CartBook>>(mutableListOf())

    var bookDetailComments = mutableListOf<Comment>()
    var bookRatings = 0
    var ratedPeople = 0


    var allRateBook = mutableListOf<Rating>()
    var isFilter = false
    var minPrice = 0
    var maxPrice = 10000
    var priceValues = minPrice.toFloat()..maxPrice.toFloat()




    var myRatingInBook = Rating()
    var i_Im_rated_this_book = false





    init {
        viewModelScope.launch {
            books = getBookList().toMutableList()
        }
    }

    private suspend fun getBookList(): List<Book> {
        return Firebase.firestore.collection("books").get().await().map { book ->
            book.toObject(Book::class.java)
        }
    }

    fun getCart(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            userCart = getCartItemsList(userId).toMutableList()
        }
    }
    private suspend fun getCartItemsList(userId: String): List<CartBook> {
        return Firebase.firestore.collection("cart")
            .whereEqualTo("addedUserId", user.userId)
            .get().await().map { book ->
                book.toObject(CartBook::class.java)
            }
    }

    fun getComment(book : Book){
        viewModelScope.launch(Dispatchers.IO) {
            bookDetailComments = showComments(book).toMutableList()
        }
    }
    private suspend fun showComments(book: Book) : List<Comment>{
        return Firebase.firestore.collection("Comments")
            .whereEqualTo("book_id",book.bookId)
            .get().await().map { comment ->
                comment.toObject(Comment::class.java)
            }
    }

    fun getRate(book : Book){
        viewModelScope.launch(Dispatchers.IO) {
            bookRatings = getRating(book)
        }
    }
    private suspend fun getRating(book : Book): Int{
        var sum = 0;
        var cnt = 0;

        Firebase.firestore.collection("ratings").whereEqualTo("book_id",book.bookId)
            .get().await().map { rating ->
                sum += rating.toObject(Rating::class.java).rate
                cnt += 1
            }
        ratedPeople = cnt
        if(sum == 0 && cnt == 0){
            return 0;
        }
        return sum / cnt
    }

    fun getMyRating(user_id : String , book_id : String){
        viewModelScope.launch(Dispatchers.IO){
            getMyRat(user_id,book_id)
        }
    }



    private suspend fun getMyRat(user_id : String , book_id : String){
        Firebase.firestore.collection("ratings").whereEqualTo("user_id",user_id).whereEqualTo("book_id",book_id)
            .limit(1)
            .get()
            .addOnSuccessListener { i ->
                if(!i.isEmpty()){
                    for(j in i){
                        myRatingInBook = j.toObject(Rating::class.java)
                    }
                    i_Im_rated_this_book  = true
                }
                else{
                    myRatingInBook = Rating()
                }

            }

    }
    fun getAllBookByRate(bookRate : Int){
        viewModelScope.launch(Dispatchers.IO) {
            allRateBook = getAllBook_idByRat(bookRate).toMutableList()
            isFilter = true
            isFiltered()
        }
    }

    private suspend fun getAllBook_idByRat( bookRate : Int ) : List<Rating> {
        return Firebase.firestore.collection("ratings").whereEqualTo("rate",bookRate)
            .get()
            .await()
            .map { rating ->
                rating.toObject(Rating :: class.java)
            }
    }
    fun isFiltered(){
        if(isFilter){
            books = books.filter { book ->
                book.cost in priceValues && allRateBook.any(){ it.book_id == book.bookId }
            } as MutableList<Book>
        }
        else{
            viewModelScope.launch {
                books = getBookList().toMutableList()
            }
        }
    }

    fun handmadeInit(){
        viewModelScope.launch {
            books = getBookList().toMutableList()
        }
    }

}