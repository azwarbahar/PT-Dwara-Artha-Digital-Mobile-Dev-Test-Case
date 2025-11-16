package com.azwar.myticket.presentation.home

import com.azwar.myticket.domain.model.Ticket
import com.azwar.myticket.domain.model.TicketStatus

data class HomeUiState(
    val isLoading: Boolean = false,
    val tickets: List<Ticket> = emptyList(),
    val errorMessage: String? = null,
    val selectedFilter: TicketStatus? = null
)