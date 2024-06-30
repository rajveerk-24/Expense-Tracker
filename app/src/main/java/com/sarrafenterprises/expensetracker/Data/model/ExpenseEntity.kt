package com.sarrafenterprises.expensetracker.Data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "expense_table")
data class ExpenseEntity(

    @PrimaryKey(autoGenerate = true)

    val id: Int?,
    val title: String,
    val date: String,
    val category: String,
    val amount: Double,
    val type: String
)
