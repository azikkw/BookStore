package com.example.bookstore

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BooksFirebaseApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}