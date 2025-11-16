package com.azwar.myticket.domain.usecase

import com.azwar.myticket.domain.model.Ticket
import com.azwar.myticket.domain.repository.TicketRepository
import javax.inject.Inject

class UpdateTicketUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(ticket: Ticket) {
        val ticketWithTimestamp = ticket.copy(updatedAt = System.currentTimeMillis())
        repository.updateTicket(ticketWithTimestamp)
    }
}

