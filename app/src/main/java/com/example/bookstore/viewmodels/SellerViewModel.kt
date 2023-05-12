package com.example.bookstore.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.data.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@SuppressLint("MutableCollectionMutableState")
@HiltViewModel
class SellerViewModel
@Inject
constructor(): ViewModel(){
    var sellers by mutableStateOf<MutableList<Seller>>(mutableListOf())
    var currentSeller by mutableStateOf(Seller("", "", "", "", ""))
    var sellerDetail by  mutableStateOf(Seller("", "", "", "", ""))

    init {
        viewModelScope.launch {
            sellers = getSellersList().toMutableList()
        }
    }

    fun setCurrentUser(id : String){
        viewModelScope.launch {
            getById(id)
        }
    }

    fun currentUserIsThatStore() : Boolean{
        return currentSeller.sellerId == sellerDetail.sellerId
    }

    fun getStoreDetail(ownerId : String) : Seller{
        return sellers.filter { seller -> seller.sellerId == ownerId }[0]
    }

    private suspend fun setStoreByBookId(ownerId : String){
        Firebase.firestore.collection("sellers").get().await().map { seller ->
            var sellerOb = seller.toObject(Seller::class.java)
            if(sellerOb.sellerId == ownerId){
                sellerDetail = sellerOb
            }
        }
    }

    private suspend fun getById(id : String){
        Firebase.firestore.collection("sellers").get().await().map { seller ->
            var sellerOb = seller.toObject(Seller::class.java)
            if(sellerOb.sellerId == id){
                currentSeller = sellerOb
            }
        }
    }

    private suspend fun getSellersList() : List<Seller>{
        return Firebase.firestore.collection("sellers").get().await().map { seller ->
            seller.toObject(Seller::class.java)
        }
    }
}