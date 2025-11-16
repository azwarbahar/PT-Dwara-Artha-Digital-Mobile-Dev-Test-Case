@file:OptIn(ExperimentalMaterial3Api::class)

package com.azwar.myticket.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azwar.myticket.domain.model.Ticket
import com.azwar.myticket.domain.model.TicketStatus
import com.azwar.myticket.presentation.components.StatusBottomSheet
import com.azwar.myticket.presentation.components.TicketItemCard

@Composable
fun HomeRoute(
    onTicketClick: (Long) -> Unit,
    onAddTicketClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        onTicketClick = onTicketClick,
        onAddTicketClick = onAddTicketClick,
        onSettingsClick = onSettingsClick,
        onStatusFilterChange = viewModel::filterByStatus,
        onStatusChange = viewModel::setUpdateTicketStatus
    )
}

@Composable
fun HomeScreen(
    state: HomeUiState,
    onTicketClick: (Long) -> Unit,
    onAddTicketClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onStatusFilterChange: (TicketStatus?) -> Unit,
    onStatusChange: (Long, TicketStatus) -> Unit
) = with(state) {
    var selectedTicketForStatus by remember { mutableStateOf<Ticket?>(null) }
    var showStatusSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Tickets",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddTicketClick,
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Ticket",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFilter == null,
                    onClick = { onStatusFilterChange(null) },
                    label = { Text("All") }
                )
                FilterChip(
                    selected = selectedFilter == TicketStatus.OPEN,
                    onClick = { onStatusFilterChange(TicketStatus.OPEN) },
                    label = { Text("Open") }
                )
                FilterChip(
                    selected = selectedFilter == TicketStatus.IN_PROGRESS,
                    onClick = { onStatusFilterChange(TicketStatus.IN_PROGRESS) },
                    label = { Text("In Progress") }
                )
                FilterChip(
                    selected = selectedFilter == TicketStatus.DONE,
                    onClick = { onStatusFilterChange(TicketStatus.DONE) },
                    label = { Text("Done") }
                )
            }

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    isLoading -> {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }

                    errorMessage != null -> {
                        Column(
                            Modifier
                                .align(Alignment.Center)
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = errorMessage,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    tickets.isEmpty() -> {
                        Text(
                            text = "No tickets found",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(tickets) { ticket ->
                                TicketItemCard(
                                    ticket = ticket,
                                    onClick = { onTicketClick(ticket.id) },
                                    onStatusClick = {
                                        selectedTicketForStatus = ticket
                                        showStatusSheet = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        selectedTicketForStatus?.let { ticket ->
            StatusBottomSheet(
                isVisible = showStatusSheet,
                currentStatus = ticket.status,
                onDismiss = {
                    showStatusSheet = false
                    selectedTicketForStatus = null
                },
                onStatusSelected = { status ->
                    onStatusChange(ticket.id, status)
                    showStatusSheet = false
                    selectedTicketForStatus = null
                }
            )
        }
    }
}