package com.example.incometracker.ui.budget

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.incometracker.data.entities.Budget
import com.example.incometracker.databinding.ActivitySetBudgetBinding

class BudgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetBudgetBinding
    private val budgetViewModel: BudgetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetBudgetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        budgetViewModel.budget.observe(this, Observer { budget ->
            budget?.let {
                binding.editTextBudget.setText(it.amount.toString())
            }
        })

        binding.buttonSaveBudget.setOnClickListener {
            val budgetAmount = binding.editTextBudget.text.toString().toDouble()
            val budget = Budget(1, budgetAmount)
            budgetViewModel.setBudget(budget)
            finish()
        }
    }
}