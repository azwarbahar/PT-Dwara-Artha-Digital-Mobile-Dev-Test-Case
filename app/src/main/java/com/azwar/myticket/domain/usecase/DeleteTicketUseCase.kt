package com.azwar.myticket.domain.usecase

import com.azwar.myticket.domain.repository.TicketRepository
import javax.inject.Inject

class DeleteTicketUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteTicket(id)
    }
}

