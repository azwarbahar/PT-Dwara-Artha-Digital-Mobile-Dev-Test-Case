package com.azwar.myticket.domain.repository

import com.azwar.myticket.domain.model.Ticket
import com.azwar.myticket.domain.model.TicketStatus
import kotlinx.coroutines.flow.Flow

interface TicketRepository {
    fun getAllTickets(): Flow<List<Ticket>>
    fun getTicketsByStatus(status: TicketStatus): Flow<List<Ticket>>
    suspend fun getTicketById(id: Long): Ticket?
    suspend fun createTicket(ticket: Ticket): Long
    suspend fun updateTicket(ticket: Ticket)
    suspend fun updateTicketStatus(id: Long, status: TicketStatus)
    suspend fun deleteTicket(id: Long)
}

