package com.azwar.myticket.domain.usecase

import com.azwar.myticket.domain.model.Ticket
import com.azwar.myticket.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTicketsUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    operator fun invoke(): Flow<List<Ticket>> = repository.getAllTickets()
}

