package com.azwar.myticket.domain.usecase

import com.azwar.myticket.domain.model.TicketStatus
import com.azwar.myticket.domain.repository.TicketRepository
import javax.inject.Inject

class UpdateTicketStatusUseCase @Inject constructor(
    private val repository: TicketRepository
) {
    suspend operator fun invoke(id: Long, status: TicketStatus) {
        repository.updateTicketStatus(id, status)
    }
}

