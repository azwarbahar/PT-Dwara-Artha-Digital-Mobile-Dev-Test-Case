@file:OptIn(ExperimentalMaterial3Api::class)

package com.azwar.myticket.presentation.newticket

import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.exitUntilCollapsedScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.azwar.myticket.domain.model.TicketCategory
import com.azwar.myticket.presentation.components.CategoryBottomSheet
import com.azwar.myticket.presentation.components.CategoryField

@Composable
fun NewTicketRoute(
    onBack: () -> Unit,
    onTicketCreated: () -> Unit,
    viewModel: NewTicketViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    NewTicketScreen(
        state = state,
        onBack = onBack,
        onTitleChange = viewModel::updateTitle,
        onDescriptionChange = viewModel::updateDescription,
        onCategoryChange = viewModel::updateCategory,
        onSubmit = {
            viewModel.createTicket(onSuccess = onTicketCreated)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTicketScreen(
    state: NewTicketUiState,
    onBack: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCategoryChange: (TicketCategory) -> Unit,
    onSubmit: () -> Unit
) = with(state) {
    val scrollBehavior = exitUntilCollapsedScrollBehavior()
    var showCategorySheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(
                        text = "New Ticket",
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
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.surfaceContainer
                ),
                elevation = cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = title,
                        onValueChange = onTitleChange,
                        label = { Text("Title *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = titleError != null,
                        supportingText = titleError?.let { { Text(it) } },
                        colors = OutlinedTextFieldDefaults.colors()
                    )

                    OutlinedTextField(
                        value = description,
                        onValueChange = onDescriptionChange,
                        label = { Text("Description *") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        maxLines = 5,
                        isError = descriptionError != null,
                        supportingText = descriptionError?.let { { Text(it) } },
                        colors = OutlinedTextFieldDefaults.colors()
                    )

                    CategoryField(
                        selectedCategory = category,
                        onCategoryClick = { showCategorySheet = true }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onSubmit,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = !isLoading
                    ) {
                        Text(
                            text = if (isLoading) "Creating..." else "Create Ticket",
                            fontWeight = Bold
                        )
                    }

                    if (errorMessage != null) {
                        Text(
                            text = errorMessage,
                            color = colorScheme.error,
                            style = typography.bodySmall
                        )
                    }
                }
            }
        }

        CategoryBottomSheet(
            isVisible = showCategorySheet,
            currentCategory = state.category,
            onDismiss = { showCategorySheet = false },
            onCategorySelected = { category ->
                onCategoryChange(category)
                showCategorySheet = false
            }
        )
    }
}