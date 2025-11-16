package com.azwar.myticket.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azwar.myticket.domain.model.TicketStatus
import com.azwar.myticket.domain.usecase.GetAllTicketsUseCase
import com.azwar.myticket.domain.usecase.GetTicketsByStatusUseCase
import com.azwar.myticket.domain.usecase.UpdateTicketStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTickets: GetAllTicketsUseCase,
    private val getTicketsByStatus: GetTicketsByStatusUseCase,
    private val updateTicketStatus: UpdateTicketStatusUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var currentFlowJob: kotlinx.coroutines.Job? = null

    init {
        loadTickets(null)
    }

    fun filterByStatus(status: TicketStatus?) {
        _uiState.value = _uiState.value.copy(selectedFilter = status)
        loadTickets(status)
    }

    private fun loadTickets(status: TicketStatus?) {
        currentFlowJob?.cancel()
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        currentFlowJob = if (status == null) {
            getAllTickets()
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error loading tickets"
                    )
                }
                .onEach { tickets ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        tickets = tickets
                    )
                }
                .launchIn(viewModelScope)
        } else {
            getTicketsByStatus(status)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Error loading tickets"
                    )
                }
                .onEach { tickets ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        tickets = tickets
                    )
                }
                .launchIn(viewModelScope)
        }
    }

    fun setUpdateTicketStatus(ticketId: Long, status: TicketStatus) {
        viewModelScope.launch {
            try {
                updateTicketStatus(ticketId, status)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Error updating ticket status: ${e.message ?: "Unknown error"}"
                )
                delay(3000)
                _uiState.value = _uiState.value.copy(errorMessage = null)
            }
        }
    }
}