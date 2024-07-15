package com.sarrafenterprises.expensetracker.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sarrafenterprises.expensetracker.Data.model.ExpenseEntity
import com.sarrafenterprises.expensetracker.viewModel.TransactionViewModel
import com.sarrafenterprises.expensetracker.viewModel.TransactionViewModelFatory
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistory(navController: NavController) {

    val viewModel: TransactionViewModel = TransactionViewModelFatory(LocalContext.current).create(
        TransactionViewModel::class.java
    )

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(

                title = {
                    Box(modifier = Modifier.fillMaxWidth()){
                Text(text = "Transaction History", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.TopCenter))
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
                            Modifier
                                .align(Alignment.CenterStart)
                                .clickable {
                                    navController.popBackStack()})
            }}, colors = TopAppBarDefaults.topAppBarColors(Color(0xFF33847E))
            )

        }
        ) { innerpading ->

        Column(
            modifier = Modifier
                .padding(innerpading)
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val state = viewModel.expenses.collectAsState(initial = emptyList())
            AllTransaction(modifier = Modifier.fillMaxWidth(), list = state.value, viewModel)

        }
    }
}

@Composable
fun AllTransaction(modifier: Modifier, list: List<ExpenseEntity>, viewModel: TransactionViewModel) {
    val sortedList = remember(list) {
        list.sortedByDescending { it.date }
    }

    LazyColumn(modifier= modifier) {

        items(sortedList ){ item ->
            TransactionItem1(
                title = item.title,
                amount = if (item.type == "Income") "+ ₹ ${item.amount }" else "- ₹ ${item.amount}",
                icon = viewModel.getItemIcon(item),
                date = getDateLabel1(item.date),
                color = if (item.type == "Income") Color.Green else Color.Red
            )
        }
    }

}



@Composable
fun TransactionItem1(title: String, amount: String, icon: Int, date: String, color: Color){

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {

        Row {
            Image(painter = painterResource(id = icon), contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .size(40.dp)
                    .padding(5.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Column {
                Text(text = title, fontSize = 16.sp, modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 5.dp), fontWeight = FontWeight.Medium)
                HorizontalDivider(thickness = 2.dp, modifier = Modifier
                    .width(200.dp)
                    .padding(start = 5.dp, end = 5.dp))
                Text(text = date, fontSize = 16.sp, color = Color.Gray, modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 5.dp))
            }
        }
        Text(text = amount, fontSize = 18.sp, modifier = Modifier.align(Alignment.CenterEnd),
            fontWeight = FontWeight.SemiBold, color = color)
    }

}

@Composable
fun getDateLabel1(date: String): String {
    val calendar = Calendar.getInstance()
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    val currentMonth = calendar.get(Calendar.MONTH) + 1 // Adding 1 because Calendar.MONTH is zero-based

    val parts = date.split("/")
    if (parts.size == 3) {
        val day = parts[0].toInt()
        val month = parts[1].toInt()

        return when {
            day == currentDay && month == currentMonth -> "Today"
            day == currentDay - 1 && month == currentMonth -> "Yesterday"
            else -> date // fallback to actual date if not today or yesterday
        }
    }
    return date // fallback to actual date if date format is incorrect
}