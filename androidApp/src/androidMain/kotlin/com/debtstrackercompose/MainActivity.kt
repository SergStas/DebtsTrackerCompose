package com.debtstrackercompose

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import appContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DebtsTracker)
        super.onCreate(savedInstanceState)
        appContext = applicationContext
        setContent {
            MainView()
        }
    }
}