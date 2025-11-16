package com.azwar.myticket.domain.model

enum class TicketStatus {
    OPEN,
    IN_PROGRESS,
    DONE
}

enum class TicketCategory {
    IT_SUPPORT,
    HR,
    FACILITIES,
    FINANCE,
    GENERAL
}

data class Ticket(
    val id: Long = 0,
    val title: String,
    val description: String,
    val category: TicketCategory,
    val status: TicketStatus,
    val createdAt: Long,
    val updatedAt: Long
)

