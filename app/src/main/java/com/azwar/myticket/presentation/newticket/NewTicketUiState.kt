package com.azwar.myticket.presentation.newticket

import com.azwar.myticket.domain.model.TicketCategory
import com.azwar.myticket.domain.model.TicketCategory.GENERAL

data class NewTicketUiState(
    val title: String = "",
    val description: String = "",
    val category: TicketCategory = GENERAL,
    val titleError: String? = null,
    val descriptionError: String? = null,
    val categoryError: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)