package com.sarrafenterprises.expensetracker.Data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sarrafenterprises.expensetracker.Data.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {


    @Query("SELECT * FROM expense_table")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Insert
    suspend fun insertExpense(expenseEntity : ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expenseEntity : ExpenseEntity)

    @Update
    suspend fun updateExpense(expenseEntity : ExpenseEntity)

    @Query("DELETE FROM expense_table")
    suspend fun deleteAllExpenses()
}