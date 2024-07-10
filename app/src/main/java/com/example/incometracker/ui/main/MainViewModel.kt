package com.example.incometracker.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.incometracker.data.entities.Budget
import com.example.incometracker.data.entities.Transaction
import com.example.incometracker.data.repository.BudgetRepository
import com.example.incometracker.data.repository.TransactionRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TransactionRepository
    private val repositoryBudget: BudgetRepository
    val allTransactions: LiveData<List<Transaction>>
    val budget: LiveData<Budget>

    init {
        val transactionDao = com.example.incometracker.data.database.AppDatabase.getDatabase(application).transactionDao()
        val budgetDao = com.example.incometracker.data.database.AppDatabase.getDatabase(application).budgetDao()
        repositoryBudget = BudgetRepository(budgetDao)
        repository = TransactionRepository(transactionDao)
        allTransactions = repository.allTransactions
        budget = repositoryBudget.budget
    }

    fun delete(id: Int) = viewModelScope.launch {
        repository.delete(id)
    }
}