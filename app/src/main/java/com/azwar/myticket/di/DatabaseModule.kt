package com.azwar.myticket.di

import android.content.Context
import androidx.room.Room
import com.azwar.myticket.data.local.MyTicketDatabase
import com.azwar.myticket.data.local.dao.TicketDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MyTicketDatabase {
        return Room.databaseBuilder(
            context,
            MyTicketDatabase::class.java,
            "myticket_database"
        ).build()
    }

    @Provides
    fun provideTicketDao(database: MyTicketDatabase): TicketDao {
        return database.ticketDao()
    }
}

