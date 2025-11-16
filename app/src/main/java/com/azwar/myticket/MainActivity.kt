package com.azwar.myticket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.azwar.myticket.presentation.navigation.AppNavHost
import com.azwar.myticket.presentation.settings.DarkModeState
import com.azwar.myticket.ui.theme.MyTicketTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    val darkMode by DarkModeState.darkModeState
    MyTicketTheme(darkTheme = darkMode) {
        Surface(modifier = Modifier.fillMaxSize()) {
            AppNavHost()
        }
    }
}

