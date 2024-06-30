package com.sarrafenterprises.expensetracker.Data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sarrafenterprises.expensetracker.Data.dao.ExpenseDao
import com.sarrafenterprises.expensetracker.Data.model.ExpenseEntity


@Database(entities = [ExpenseEntity::class], version = 1)
abstract class ExpenseDataBase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao

    companion object{
        const val DATABASE_NAME = "expense_database"

        @JvmStatic
        fun getInstance(context: Context): ExpenseDataBase {
            return Room.databaseBuilder(context, ExpenseDataBase::class.java, DATABASE_NAME)
                .build()
        }
    }

}