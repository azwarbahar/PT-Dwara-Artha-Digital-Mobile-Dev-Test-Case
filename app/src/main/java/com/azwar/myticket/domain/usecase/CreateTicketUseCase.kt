package com.azwar.myticket.domain.usecase

import com.azwar.myticket.domain.model.Ticket
import com.azwar.myticket.domain.repository.TicketRepository
import javax.inject.Inject

class CreateTicketUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(ticket: Ticket): Long {
        val ticketWithTimestamp = ticket.copy(
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        return repository.createTicket(ticketWithTimestamp)
    }
}

