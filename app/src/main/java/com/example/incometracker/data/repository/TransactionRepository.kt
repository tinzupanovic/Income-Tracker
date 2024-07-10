package com.example.incometracker.data.repository

import androidx.lifecycle.LiveData
import com.example.incometracker.data.dao.TransactionDao
import com.example.incometracker.data.entities.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {
    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAllTransactions()

    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    suspend fun delete(id: Int) {
        transactionDao.delete(id)
    }
}