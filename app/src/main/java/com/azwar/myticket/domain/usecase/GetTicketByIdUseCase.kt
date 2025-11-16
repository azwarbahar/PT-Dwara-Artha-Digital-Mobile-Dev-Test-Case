package com.azwar.myticket.domain.usecase

import com.azwar.myticket.domain.model.Ticket
import com.azwar.myticket.domain.repository.TicketRepository
import javax.inject.Inject

class GetTicketByIdUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(id: Long): Ticket? = repository.getTicketById(id)
}

