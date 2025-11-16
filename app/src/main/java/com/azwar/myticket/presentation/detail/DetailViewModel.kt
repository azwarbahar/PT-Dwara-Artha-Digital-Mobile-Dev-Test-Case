package com.azwar.myticket.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azwar.myticket.domain.usecase.GetTicketByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getTicketById: GetTicketByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val ticketId: Long = savedStateHandle.get<Long>("ticketId") ?: 0L

    private val _uiState = MutableStateFlow(DetailUiState(isLoading = true))
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadTicket()
    }

    private fun loadTicket() {
        if (ticketId == 0L) {
            _uiState.value = DetailUiState(
                isLoading = false,
                errorMessage = "Invalid ticket ID"
            )
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            try {
                val ticket = getTicketById(ticketId)
                _uiState.value = DetailUiState(
                    isLoading = false,
                    ticket = ticket,
                    errorMessage = if (ticket == null) "Ticket not found" else null
                )
            } catch (e: Exception) {
                _uiState.value = DetailUiState(
                    isLoading = false,
                    errorMessage = e.message ?: "Error loading ticket"
                )
            }
        }
    }
}

