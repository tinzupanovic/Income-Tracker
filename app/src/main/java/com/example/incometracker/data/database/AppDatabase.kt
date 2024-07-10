package com.example.incometracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.incometracker.data.dao.BudgetDao
import com.example.incometracker.data.dao.TransactionDao
import com.example.incometracker.data.entities.Budget
import com.example.incometracker.data.entities.Transaction

@Database(entities = [Transaction::class, Budget::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()
                    .build().also { instance = it }
            }
    }
}