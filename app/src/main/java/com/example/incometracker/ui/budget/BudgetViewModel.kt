package com.example.incometracker.ui.budget

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.incometracker.data.database.AppDatabase
import com.example.incometracker.data.entities.Budget
import com.example.incometracker.data.repository.BudgetRepository
import kotlinx.coroutines.launch

class BudgetViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BudgetRepository
    val budget: LiveData<Budget>

    init {
        val budgetDao = AppDatabase.getDatabase(application).budgetDao()
        repository = BudgetRepository(budgetDao)
        budget = repository.budget
    }

    fun setBudget(budget: Budget) = viewModelScope.launch {
        repository.setBudget(budget)
    }
}