package com.sarrafenterprises.expensetracker.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.End
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
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.sarrafenterprises.expensetracker.Data.model.ExpenseEntity
import com.sarrafenterprises.expensetracker.R
import com.sarrafenterprises.expensetracker.viewModel.HomeViewModel
import com.sarrafenterprises.expensetracker.viewModel.HomeViewModelFatory
import kotlinx.coroutines.launch
import java.util.Calendar

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen(navController: NavController) {


    val viewModel :HomeViewModel = HomeViewModelFatory(LocalContext.current).create(HomeViewModel::class.java)
Surface(modifier = Modifier.fillMaxSize()) {

ConstraintLayout(modifier = Modifier.fillMaxSize()) {

    val (nameRow, list, card, topBar, add) = createRefs()
    Image(painter = painterResource(id = R.drawable.ic_topbar), contentDescription = null,
        modifier = Modifier.constrainAs(topBar) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 30.dp, start = 15.dp, end = 15.dp)
        .constrainAs(nameRow) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }) {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)


        Column {
            Text(text = when {
                currentHour < 12 -> "Good morning," // Morning (4 AM to 12 PM)
                currentHour < 16 -> "Good afternoon," // Afternoon (12 PM to 4 PM)
                else -> "Good evening," // Evening (after 4 PM)
            }, fontSize = 16.sp, color = Color.White)
            Text(
                text = "Admin",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

        }
        Icon(
            Icons.Outlined.Notifications,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.CenterEnd)
        )
    }

    val state = viewModel.expenses.collectAsState(initial = emptyList())
    val balance = viewModel.getBalance(state.value)
    val expenses = viewModel.getTotalExpense(state.value)
    val income = viewModel.getTotalIncome(state.value)
    val scope = rememberCoroutineScope()

    CardItem(modifier = Modifier.constrainAs(card) {
        top.linkTo(nameRow.bottom)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }, balance = balance, expenses = expenses, income = income, onResetClick = {
        scope.launch {

            viewModel.deleteAllExpenses()
        }
    })

    Box(modifier = Modifier
        .fillMaxWidth()
        .constrainAs(list) {
            top.linkTo(card.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            height = Dimension.fillToConstraints

        }
    ) {
    TransactionList(modifier = Modifier.padding(horizontal = 10.dp), list = state.value, viewModel, navController )
        Text(text = "Made with ❤ in India by Rajveer", fontSize = 8.sp, modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(30.dp))
    }

    FloatingActionButton(
        onClick = {
            navController.navigate(ScreenBHolder)
        },
        modifier = Modifier.constrainAs(add){
            bottom.linkTo(parent.bottom, margin = 16.dp)
            end.linkTo(parent.end, margin = 16.dp)
        }
    ) {
        Icon(Icons.Filled.Create, "Floating action button.")
    }
}
}
}

@Composable
fun CardItem(modifier: Modifier = Modifier, balance: String, expenses: String, income: String,
             onResetClick: () -> Unit){
    var expanded = remember { mutableStateOf(false) }
    Column(modifier = modifier
        .padding(20.dp)
        .fillMaxWidth()
        .clip(RoundedCornerShape(14.dp))
        .background(
            brush = Brush.linearGradient(
                listOf(Color.DarkGray, Color.Gray)
            )
        )
        .padding(20.dp)
        .height(190.dp)
        ) {
        Box(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(top = 10.dp)){
           Text(text = "Total Balance", fontSize = 20.sp, color = Color.White,fontWeight = FontWeight.Bold)
            Icon(Icons.Outlined.KeyboardArrowUp, contentDescription = null, tint = Color.White, modifier = Modifier
                .size(22.dp)
                .padding(start = 5.dp))
        }
            Box(modifier = Modifier.align(Alignment.CenterEnd)) {
                Image(painter = painterResource(id = R.drawable.dots_menu),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(top = 20.dp)
                        .size(20.dp)
                        .clickable {
                            expanded.value = true
                        }
                )
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    DropdownMenuItem(text = { Text("Reset") }, onClick = {
                        onResetClick()
                        expanded.value = false
                    })
                }
            }
}

        Text(text = balance, fontSize = 26.sp, color = Color.White,fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(30.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier
                .padding(top = 20.dp)
                .weight(1f),
                horizontalAlignment = Alignment.Start
                ) {
                Row {
                    Image(painter = painterResource(id = R.drawable.ic_income), contentDescription = null
                        , modifier = Modifier
                            .size(25.dp))
                    Text(text = " Income", fontSize = 18.sp, color = Color.White,fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = income, fontSize = 20.sp, color = Color.White,fontWeight = FontWeight.Bold)
            }

            Column(modifier = Modifier
                .padding(top = 20.dp)
                .weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                Row {
                    Image(painter = painterResource(id = R.drawable.ic_expense), contentDescription = null
                        , modifier = Modifier
                            .size(25.dp))
                    Text(text = " Expenses", fontSize = 18.sp, color = Color.White,fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = expenses, fontSize = 20.sp, color = Color.White,fontWeight = FontWeight.Bold)
            }
        }

    }
}

@Composable
fun TransactionList(modifier: Modifier, list: List<ExpenseEntity>, viewModel: HomeViewModel, navController: NavController){
    val sortedList = remember(list) {
        list.sortedByDescending  { it.date }
    }
    val limitedList = remember(sortedList) {
        sortedList.take(5)
    }

    LazyColumn(modifier= modifier ) {
        item {
            Box (modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)){
                Text(text = "Recent Transaction", fontSize = 20.sp,fontWeight = FontWeight.Bold,modifier = Modifier
                    .align(Alignment.CenterStart))
                Text(text = "See all",
                    fontSize = 16.sp,
                    color = Color.Cyan,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            navController.navigate(TransactionHolder)
                        })
            }
        }

        items(limitedList ){ item ->
            TransactionItem(
                title = item.title,
                amount = if (item.type == "Income") "+ ₹ ${item.amount }" else "- ₹ ${item.amount}",
                icon = viewModel.getItemIcon(item),
                date = getDateLabel(item.date),
                color = if (item.type == "Income") Color.Green else Color.Red,
                viewModel,
                item = item
            )
        }
    }
}

@Composable
fun TransactionItem(title: String, amount: String, icon: Int, date: String, color: Color, viewModel: HomeViewModel, item: ExpenseEntity){

    val scope = rememberCoroutineScope()
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {

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
        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            Text(text = amount, fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold, color = color)

            Icon(Icons.Filled.Delete, contentDescription = null,
                modifier = Modifier.clickable {
                    scope.launch {
                        viewModel.deleteExpense(item)
                    }
                })

        }
    }

}
@Composable
fun getDateLabel(date: String): String {
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