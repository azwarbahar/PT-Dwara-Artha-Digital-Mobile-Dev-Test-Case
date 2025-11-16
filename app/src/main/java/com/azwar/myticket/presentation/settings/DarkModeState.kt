package com.azwar.myticket.presentation.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.azwar.myticket.presentation.settings.DarkModeState.darkModeState

object DarkModeState {
    private val _isDarkMode: MutableState<Boolean> = mutableStateOf(false)

    val isDarkMode: Boolean
        get() = _isDarkMode.value

    fun setDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }

    val darkModeState: MutableState<Boolean>
        get() = _isDarkMode
}

@Composable
fun isDarkMode(): Boolean {
    return darkModeState.value
}

