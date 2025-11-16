package com.azwar.myticket.presentation.detail

import com.azwar.myticket.domain.model.Ticket

data class DetailUiState(
    val isLoading: Boolean = false,
    val ticket: Ticket? = null,
    val errorMessage: String? = null
)