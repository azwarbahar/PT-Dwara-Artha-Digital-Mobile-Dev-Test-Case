package com.azwar.myticket.presentation.newticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azwar.myticket.domain.model.Ticket
import com.azwar.myticket.domain.model.TicketCategory
import com.azwar.myticket.domain.model.TicketStatus
import com.azwar.myticket.domain.usecase.CreateTicketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewTicketViewModel @Inject constructor(
    private val createTicket: CreateTicketUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewTicketUiState())
    val uiState: StateFlow<NewTicketUiState> = _uiState.asStateFlow()

    fun updateTitle(title: String) {
        _uiState.value = _uiState.value.copy(
            title = title,
            titleError = null,
            errorMessage = null
        )
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(
            description = description,
            descriptionError = null,
            errorMessage = null
        )
    }

    fun updateCategory(category: TicketCategory) {
        _uiState.value = _uiState.value.copy(
            category = category,
            categoryError = null,
            errorMessage = null
        )
    }

    fun createTicket(onSuccess: () -> Unit) {
        val state = _uiState.value
        var hasError = false

        val titleError = if (state.title.isBlank()) {
            hasError = true
            "Title is required"
        } else null

        val descriptionError = if (state.description.isBlank()) {
            hasError = true
            "Description is required"
        } else null

        if (hasError) {
            _uiState.value = state.copy(
                titleError = titleError,
                descriptionError = descriptionError
            )
            return
        }

        _uiState.value = state.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            try {
                val ticket = Ticket(
                    title = state.title.trim(),
                    description = state.description.trim(),
                    category = state.category,
                    status = TicketStatus.OPEN,
                    createdAt = 0,
                    updatedAt = 0
                )
                createTicket(ticket)
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = state.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Error creating ticket"
                )
            }
        }
    }
}