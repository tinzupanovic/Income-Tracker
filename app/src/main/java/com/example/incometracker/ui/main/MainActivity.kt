package com.example.incometracker.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.incometracker.data.entities.Transaction
import com.example.incometracker.databinding.ActivityMainBinding
import com.example.incometracker.ui.budget.BudgetActivity
import com.example.incometracker.ui.transaction.TransactionActivity

class MainActivity : AppCompatActivity(), TransactionListAdapter.CallbackDelete {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private var allTransactions: List<Transaction> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = TransactionListAdapter(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        mainViewModel.allTransactions.observe(this, Observer { transactions ->
            transactions?.let {
                allTransactions = it
                adapter.submitList(it)
                updateBalance(it)
            }
        })

        mainViewModel.budget.observe(this, Observer { budget ->
            budget?.let {
                updateBalance(mainViewModel.allTransactions.value ?: emptyList())
            }
        })

        binding.buttonAdd.setOnClickListener {
            startActivity(Intent(this, TransactionActivity::class.java))
        }

        binding.buttonSetBudget.setOnClickListener {
            startActivity(Intent(this, BudgetActivity::class.java))
        }

        binding.buttonFilterAll.setOnClickListener {
            adapter.submitList(allTransactions)
        }

        binding.buttonFilterIncome.setOnClickListener {
            val incomeTransactions = allTransactions.filter { it.type == "Income" }
            adapter.submitList(incomeTransactions)
        }

        binding.buttonFilterSpending.setOnClickListener {
            val spendingTransactions = allTransactions.filter { it.type == "Spending" }
            adapter.submitList(spendingTransactions)
        }
    }

    private fun updateBalance(transactions: List<Transaction>) {
        val balance = transactions.sumOf {
            if (it.type == "Income") it.amount else -it.amount
        }
        binding.textViewBalance.text = "Balance: $%.2f".format(balance)

        mainViewModel.budget.value?.let { budget ->
            if (balance < budget.amount) {
                binding.textViewBalance.setTextColor(Color.RED)
            } else {
                binding.textViewBalance.setTextColor(Color.GREEN)
            }
        }
    }

    override fun onDelete(id: Int) {
        mainViewModel.delete(id)
    }
}