package com.sarrafenterprises.expensetracker.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sarrafenterprises.expensetracker.Data.ExpenseDataBase
import com.sarrafenterprises.expensetracker.Data.dao.ExpenseDao
import com.sarrafenterprises.expensetracker.Data.model.ExpenseEntity

class AddExpenseViewModel(val dao: ExpenseDao): ViewModel() {

    suspend fun addExpense(expenseEntity: ExpenseEntity): Boolean{
        try {
            dao.insertExpense(expenseEntity)
            return true
        }catch (e: Exception){
            return false
        }
    }
}
class AddExpenseViewModelFatory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddExpenseViewModel::class.java)) {
            val dao = ExpenseDataBase.getInstance(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return AddExpenseViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
