package com.azwar.myticket.domain.usecase

import com.azwar.myticket.domain.model.Ticket
import com.azwar.myticket.domain.model.TicketStatus
import com.azwar.myticket.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTicketsByStatusUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    operator fun invoke(status: TicketStatus): Flow<List<Ticket>> = repository.getTicketsByStatus(status)
}

