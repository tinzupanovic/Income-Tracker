package com.example.incometracker.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.incometracker.data.entities.Transaction
import com.example.incometracker.databinding.TransactionItemBinding

class TransactionListAdapter(
    private val callbackDelete: CallbackDelete
) : ListAdapter<Transaction, TransactionListAdapter.TransactionViewHolder>(TransactionsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val binding = TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, callbackDelete)
    }

    class TransactionViewHolder(private val binding: TransactionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction, callbackDelete: CallbackDelete) {
            binding.textViewDescription.text = transaction.description
            binding.textViewAmount.text = transaction.amount.toString()
            binding.textViewDate.text = transaction.date
            binding.textViewType.text = transaction.type

            binding.deleteButton.setOnClickListener {
                callbackDelete.onDelete(transaction.id)
            }
        }
    }

    class TransactionsComparator : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.description == newItem.description && oldItem.amount == newItem.amount && oldItem.type == newItem.type && oldItem.date == newItem.date
        }
    }

    interface CallbackDelete {
        fun onDelete(id: Int)
    }
}