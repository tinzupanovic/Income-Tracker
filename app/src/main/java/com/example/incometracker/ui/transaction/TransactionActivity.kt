package com.example.incometracker.ui.transaction

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.incometracker.R
import com.example.incometracker.data.entities.Transaction
import com.example.incometracker.databinding.ActivityAddEntryBinding
import java.time.LocalDate
import java.util.Calendar

class TransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEntryBinding
    private val transactionViewModel: TransactionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var spintype = "Income"

        val transactionTypes = resources.getStringArray(R.array.transaction_type)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, transactionTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerType.adapter = adapter
        binding.spinnerType.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spintype = p0?.getItemAtPosition(p2) as String
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        binding.buttonSave.setOnClickListener {
            val description = binding.editTextDescription.text.toString()
            val amount = binding.editTextAmount.text.toString().toDouble()
            val date = binding.editTextDate.text.toString()
            val type = spintype
            val transaction = Transaction(0, description, amount, date, type)
            transactionViewModel.saveTransaction(transaction)
            finish()
        }

        binding.editTextDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            val selectedDate = LocalDate.of(selectedYear, selectedMonth, selectedDay)
            binding.editTextDate.setText(selectedDate.toString())
        }, year, month, day)

        datePickerDialog.show()
    }
}