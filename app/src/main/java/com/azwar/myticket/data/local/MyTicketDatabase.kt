package com.azwar.myticket.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.azwar.myticket.data.local.dao.TicketDao
import com.azwar.myticket.data.local.entity.TicketEntity

@Database(
    entities = [TicketEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MyTicketDatabase : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
}

