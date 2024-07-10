package com.example.incometracker.data.repository

import androidx.lifecycle.LiveData
import com.example.incometracker.data.dao.BudgetDao
import com.example.incometracker.data.entities.Budget

class BudgetRepository(private val budgetDao: BudgetDao) {
    val budget: LiveData<Budget> = budgetDao.getBudget()

    suspend fun setBudget(budget: Budget) {
        budgetDao.insert(budget)
    }
}