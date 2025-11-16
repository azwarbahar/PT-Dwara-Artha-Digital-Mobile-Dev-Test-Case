@file:OptIn(ExperimentalMaterial3Api::class)

package com.azwar.myticket.presentation.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SettingsRoute(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    SettingsScreen(
        state = state,
        onBack = onBack,
        onDarkModeToggle = viewModel::toggleDarkMode
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    state: SettingsUiState,
    onBack: () -> Unit,
    onDarkModeToggle: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontWeight = Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = cardColors(containerColor = colorScheme.surfaceContainer),
                elevation = cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(colorScheme.primaryContainer),
                            contentAlignment = Center
                        ) {
                            Icon(
                                imageVector = Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = colorScheme.onPrimaryContainer
                            )
                        }
                        Column {
                            Text(
                                text = state.userName,
                                style = typography.titleMedium,
                                fontWeight = Bold
                            )
                            Text(
                                text = state.userEmail,
                                style = typography.bodyMedium,
                                color = colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = state.userJob,
                                style = typography.bodySmall,
                                color = colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = cardColors(
                    containerColor = colorScheme.surfaceContainer
                ),
                elevation = cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = if (state.isDarkMode) Default.DarkMode else Default.LightMode,
                            contentDescription = null,
                            tint = colorScheme.primary
                        )
                        Text(
                            text = "Appearance",
                            style = typography.titleMedium,
                            fontWeight = Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = CenterVertically
                    ) {
                        Text(
                            text = "Dark Mode",
                            style = typography.bodyLarge
                        )
                        Switch(
                            checked = state.isDarkMode,
                            onCheckedChange = onDarkModeToggle
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = cardColors(
                    containerColor = colorScheme.surfaceContainer
                ),
                elevation = cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = CenterVertically,
                        horizontalArrangement = spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Default.Info,
                            contentDescription = null,
                            tint = colorScheme.primary
                        )
                        Text(
                            text = "About",
                            style = typography.titleMedium,
                            fontWeight = Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "MyTicket",
                        style = typography.titleSmall,
                        fontWeight = Bold
                    )
                    Text(
                        text = "Version 1.0.0",
                        style = typography.bodyMedium,
                        color = colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Aplikasi untuk mengelola permintaan layanan internal. Memungkinkan pengguna untuk membuat, melihat, dan mengubah status tiket.",
                        style = typography.bodyMedium,
                        color = colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}