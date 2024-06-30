package com.sarrafenterprises.expensetracker.Screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Composable
fun NavHostScreen() {

    val navController = rememberNavController()
    
    NavHost(navController = navController,
        startDestination = HScreen){

        composable<HScreen>{
            HomeScreen(navController)
        }
        composable<ScreenBHolder>{
            val obj1 = it.toRoute<ScreenBHolder>()
            AddExpense(navController)
        }
        composable<TransactionHolder>{
            val obj2 = it.toRoute<TransactionHolder>()
            TransactionHistory(navController)
        }
    }

}

@Serializable
object HScreen

@Serializable
object ScreenBHolder

@Serializable
object TransactionHolder

