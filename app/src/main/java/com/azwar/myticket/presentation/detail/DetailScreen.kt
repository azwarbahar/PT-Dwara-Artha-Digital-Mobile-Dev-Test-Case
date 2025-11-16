@file:OptIn(ExperimentalMaterial3Api::class)

package com.azwar.myticket.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azwar.myticket.presentation.components.CategoryChip
import com.azwar.myticket.presentation.components.InfoCard
import com.azwar.myticket.presentation.components.InfoRow
import com.azwar.myticket.presentation.components.StatusChip
import com.azwar.myticket.presentation.components.formatDate

@Composable
fun DetailRoute(
    onBack: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    DetailScreen(
        state = state,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    state: DetailUiState,
    onBack: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = state.ticket?.title ?: "Ticket Detail",
                        maxLines = 1,
                        overflow = Ellipsis,
                        fontWeight = Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Default.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { padding ->
        when {
            state.isLoading -> {
                CircularProgressIndicator(Modifier.padding(padding))
            }

            state.errorMessage != null -> {
                Text(
                    text = state.errorMessage,
                    color = colorScheme.error,
                    modifier = Modifier
                        .padding(padding)
                        .padding(16.dp)
                )
            }

            state.ticket != null -> {
                val ticket = state.ticket
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
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = CenterVertically
                            ) {
                                Text(
                                    text = "Status",
                                    style = typography.titleSmall,
                                    color = colorScheme.primary,
                                    fontWeight = Bold
                                )
                                StatusChip(
                                    status = ticket.status,
                                    onClick = {}
                                )
                            }
                        }
                    }

                    InfoCard(
                        icon = Default.Title,
                        label = "Title",
                        value = ticket.title
                    )

                    InfoCard(
                        icon = Default.Description,
                        label = "Description",
                        value = ticket.description
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = cardColors(containerColor = colorScheme.surfaceContainer),
                        elevation = cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = spacedBy(8.dp)
                        ) {
                            Row(
                                verticalAlignment = CenterVertically,
                                horizontalArrangement = spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Default.Category,
                                    contentDescription = null,
                                    tint = colorScheme.primary
                                )
                                Text(
                                    text = "Category",
                                    style = typography.titleSmall,
                                    color = colorScheme.primary,
                                    fontWeight = Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            CategoryChip(category = ticket.category)
                        }
                    }

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
                                horizontalArrangement = spacedBy(8.dp)
                            ) {
                                Icon(
                                    imageVector = Default.Schedule,
                                    contentDescription = null,
                                    tint = colorScheme.primary
                                )
                                Text(
                                    text = "Timestamps",
                                    style = typography.titleSmall,
                                    color = colorScheme.primary,
                                    fontWeight = Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            InfoRow("Created", formatDate(ticket.createdAt))
                            InfoRow("Updated", formatDate(ticket.updatedAt))
                        }
                    }
                }
            }
        }
    }
}