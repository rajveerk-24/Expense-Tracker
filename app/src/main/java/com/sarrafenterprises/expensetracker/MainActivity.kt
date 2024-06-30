package com.sarrafenterprises.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.sarrafenterprises.expensetracker.Screen.AddExpense
import com.sarrafenterprises.expensetracker.Screen.NavHostScreen
import com.sarrafenterprises.expensetracker.ui.theme.ExpenseTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseTrackerTheme {
/*                val isDarkTheme = isSystemInDarkTheme()
                val textColor = if (isDarkTheme) Color.White else Color.Black*/

                Surface(
                    color = MaterialTheme.colorScheme.background
                ){
            NavHostScreen()
                }
            }
        }
    }
}

