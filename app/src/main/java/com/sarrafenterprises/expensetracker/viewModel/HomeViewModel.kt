package com.sarrafenterprises.expensetracker.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sarrafenterprises.expensetracker.Data.ExpenseDataBase
import com.sarrafenterprises.expensetracker.Data.dao.ExpenseDao
import com.sarrafenterprises.expensetracker.Data.model.ExpenseEntity
import com.sarrafenterprises.expensetracker.R

class HomeViewModel(val dao: ExpenseDao) : ViewModel() {

    val expenses = dao.getAllExpenses()


    fun getBalance(list: List<ExpenseEntity>): String{
        var total = 0.0
        list.forEach{
            if (it.type == "Income"){
                total += it.amount
            }else{
                total -= it.amount

            }
        }
        return "₹ $total"
    }

    fun getTotalExpense(list: List<ExpenseEntity>): String {

        var total = 0.0
        list.forEach {
            if (it.type == "Expense") {
                total += it.amount
            }
        }
            return "₹ $total"
    }

    fun getTotalIncome(list: List<ExpenseEntity>): String {
        var total = 0.0
        list.forEach {
            if (it.type == "Income") {
                total += it.amount
            }
        }
        return "₹ $total"
    }

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

    suspend fun deleteAllExpenses() {
        dao.deleteAllExpenses()
    }

    suspend fun deleteExpense(ExpenseEntity: ExpenseEntity) {
        dao.deleteExpense(ExpenseEntity)
    }

}

class HomeViewModelFatory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            val dao = ExpenseDataBase.getInstance(context).expenseDao()
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}