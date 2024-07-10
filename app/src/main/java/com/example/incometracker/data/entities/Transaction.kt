package com.example.incometracker.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val description: String,
    val amount: Double,
    val date: String,
    val type: String
)