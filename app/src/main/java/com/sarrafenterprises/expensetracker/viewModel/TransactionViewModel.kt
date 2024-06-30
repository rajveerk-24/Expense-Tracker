package com.sarrafenterprises.expensetracker.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sarrafenterprises.expensetracker.Data.ExpenseDataBase
import com.sarrafenterprises.expensetracker.Data.dao.ExpenseDao
import com.sarrafenterprises.expensetracker.Data.model.ExpenseEntity
import com.sarrafenterprises.expensetracker.R


class TransactionViewModel(dao: ExpenseDao): ViewModel(){

    val expenses = dao.getAllExpenses()

    fun getItemIcon(item: ExpenseEntity): Int {

        return if (item.category == "Salary") {
            R.drawable.salary
        } else if (item.category == "Grocery") {
            R.drawable.grocery
        } else if (item.category == "Bills") {
            R.drawable.bills
        } else if (item.category == "Food") {
            R.drawable.food
        } else if (item.category == "Entertainment") {
            R.drawable.entertainment
        } else if (item.category == "Miscellaneous") {
            R.drawable.misc
        } else if (item.category == "Other Income") {
            R.drawable.other_income
        } else {
            R.drawable.img
        }
    }
}
class TransactionViewModelFatory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            val dao = ExpenseDataBase.getInstance(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
