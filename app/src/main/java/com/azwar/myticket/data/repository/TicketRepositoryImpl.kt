package com.azwar.myticket.data.repository

import com.azwar.myticket.data.local.dao.TicketDao
import com.azwar.myticket.data.local.entity.TicketEntity
import com.azwar.myticket.domain.model.Ticket
import com.azwar.myticket.domain.model.TicketStatus
import com.azwar.myticket.domain.repository.TicketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val ticketDao: TicketDao
) : TicketRepository {

    override fun getAllTickets(): Flow<List<Ticket>> {
        return ticketDao.getAllTickets().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTicketsByStatus(status: TicketStatus): Flow<List<Ticket>> {
        return ticketDao.getTicketsByStatus(status.name).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTicketById(id: Long): Ticket? {
        return ticketDao.getTicketById(id)?.toDomain()
    }

    override suspend fun createTicket(ticket: Ticket): Long {
        return ticketDao.insertTicket(TicketEntity.fromDomain(ticket))
    }

    override suspend fun updateTicket(ticket: Ticket) {
        ticketDao.updateTicket(TicketEntity.fromDomain(ticket))
    }

    override suspend fun updateTicketStatus(id: Long, status: TicketStatus) {
        try {
            val ticket = ticketDao.getTicketById(id)
            if (ticket != null) {
                val updatedTicket = ticket.copy(
                    status = status.name,
                    updatedAt = System.currentTimeMillis()
                )
                ticketDao.updateTicket(updatedTicket)
            }
        } catch (e: Exception) {
            throw Exception("Failed to update ticket status: ${e.message}", e)
        }
    }

    override suspend fun deleteTicket(id: Long) {
        ticketDao.deleteTicket(id)
    }
}

