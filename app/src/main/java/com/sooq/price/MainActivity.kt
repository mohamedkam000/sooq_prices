package com.sooq.price

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface

import com.sooq.price.ui.theme.*
import com.sooq.price.ui.*
import com.sooq.price.data.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = Repository(applicationContext)
        setContent {
            PricesTheme {
                Surface { AppNav(repository) }
            }
        }
    }
}