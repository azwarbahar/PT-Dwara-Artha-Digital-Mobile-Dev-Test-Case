package com.azwar.myticket.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.azwar.myticket.domain.model.Ticket
import com.azwar.myticket.domain.model.TicketCategory
import com.azwar.myticket.domain.model.TicketStatus

@Entity(tableName = "tickets")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val category: String,
    val status: String,
    val createdAt: Long,
    val updatedAt: Long
) {
    fun toDomain(): Ticket {
        return try {
            Ticket(
                id = id,
                title = title,
                description = description,
                category = TicketCategory.valueOf(category),
                status = TicketStatus.valueOf(status),
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        } catch (e: IllegalArgumentException) {
            Ticket(
                id = id,
                title = title,
                description = description,
                category = TicketCategory.GENERAL,
                status = TicketStatus.OPEN,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }

    companion object {
        fun fromDomain(ticket: Ticket): TicketEntity {
            return TicketEntity(
                id = ticket.id,
                title = ticket.title,
                description = ticket.description,
                category = ticket.category.name,
                status = ticket.status.name,
                createdAt = ticket.createdAt,
                updatedAt = ticket.updatedAt
            )
        }
    }
}

