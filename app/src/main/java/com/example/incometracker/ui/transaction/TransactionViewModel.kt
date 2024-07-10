package com.example.incometracker.ui.transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.incometracker.data.database.AppDatabase
import com.example.incometracker.data.entities.Transaction
import com.example.incometracker.data.repository.TransactionRepository
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TransactionRepository

    init {
        val transactionDao = AppDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(transactionDao)
    }
    fun saveTransaction(transaction: Transaction) {
        viewModelScope.launch { repository.insert(transaction) }
    }
}