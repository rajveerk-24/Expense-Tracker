package com.sarrafenterprises.expensetracker.Screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarrafenterprises.expensetracker.Data.model.ExpenseEntity
import com.sarrafenterprises.expensetracker.R
import com.sarrafenterprises.expensetracker.viewModel.AddExpenseViewModel
import com.sarrafenterprises.expensetracker.viewModel.AddExpenseViewModelFatory
import kotlinx.coroutines.launch
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import androidx.navigation.NavHostController


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddExpense(navController: NavController) {
    val viewModel = AddExpenseViewModelFatory(LocalContext.current).create(AddExpenseViewModel::class.java)
    val scope = rememberCoroutineScope()
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.ic_topbar),
                contentDescription = null,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            Column {
                Row(
                    modifier = Modifier
                        .padding(top = 50.dp, start = 15.dp, end = 15.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )
                    Text(
                        text = "Add Expense",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Icon(
                        Icons.Outlined.MoreVert,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .rotate(90F)
                    )
                }
            }

            DataForm(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 150.dp),
                onAddExpenseClicked = {
                    scope.launch {

                       if(viewModel.addExpense(it)){
                           navController.popBackStack()
                       }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataForm(modifier: Modifier = Modifier, onAddExpenseClicked: (model : ExpenseEntity) -> Unit) {
    val name = rememberSaveable {mutableStateOf("")}
    val amount = rememberSaveable {mutableStateOf("")}
    val date = rememberSaveable {mutableStateOf(0L)}
    val dateDialogVisibility = rememberSaveable {mutableStateOf(false)}
    val category = rememberSaveable {mutableStateOf("")}
    val type = rememberSaveable {mutableStateOf("")}

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            /*,elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
            )*/
    ) {
        val context = LocalContext.current
        Text(text = "Name", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(value = name.value, onValueChange = {
            name.value = it
        }, modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedBorderColor =  Color.Black,
                disabledTextColor = Color.Black,
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray,
                disabledPlaceholderColor = Color.Gray,
            ), keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ), singleLine = true)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Amount", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(value = amount.value, onValueChange = {
            amount.value = it
        }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedBorderColor =  Color.Black,
                disabledTextColor = Color.Black,
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray,
                disabledPlaceholderColor = Color.Gray,

            ), singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        //date

        Text(text = "Date", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(value = if (date.value == 0L) "" else Utils.formatDateToHumanReadableFormat(date.value), onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    dateDialogVisibility.value = true
                }, enabled = false,
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = Color.Black,
                disabledBorderColor = Color.Gray,
                disabledLabelColor = Color.Gray,
                disabledPlaceholderColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(20.dp))


        //category - dropdown

        Text(text = "Category", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        ExpenseCategoryDropdown(listOfItems = listOf("Select","Salary", "Grocery", "Bills", "Food", "Entertainment", "Miscellaneous", "Other Income"), onItemSelected ={
            category.value = it
        })

        Spacer(modifier = Modifier.height(16.dp))

        //type - dropdown


        Text(text = "Type", fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(4.dp))
        ExpenseTypeDropdown(listOfItems = listOf("Select","Income", "Expense"), onItemSelected ={
            type.value = it
        })

        Spacer(modifier = Modifier.height(16.dp))



        Button(onClick = {

            if (name.value.isBlank() || amount.value.isBlank() || date.value == 0L || category.value == "Select"|| type.value == "Select") {
                // Show a toast or any indication that fields are mandatory
                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val model = ExpenseEntity(
                    id = null,
                    title = name.value,
                    date = Utils.formatDateToHumanReadableFormat(date.value),
                    category = category.value,
                    amount = amount.value.toDoubleOrNull() ?: 0.0,
                    type = type.value
                )

                onAddExpenseClicked(model)

                // Clear fields after adding expense
                name.value = ""
                amount.value = ""
                date.value = 0L
                category.value = ""
                type.value = ""

                // Show success message
                Toast.makeText(context, "Expense added successfully", Toast.LENGTH_SHORT).show()
            }

        }, modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF33847E))) {
            Text(text = "Add Expense", fontSize = 16.sp, color = Color.White)
        }
if (dateDialogVisibility.value){
    ExpenseDatePickerDialog(onDateSelected = {
        date.value = it
        dateDialogVisibility.value = false
    }, onDismiss = {
        dateDialogVisibility.value = false
    })
}

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseDatePickerDialog(
    onDateSelected: (date: Long) -> Unit,
    onDismiss: () -> Unit
){
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis ?: 0L
    DatePickerDialog(onDismissRequest = { onDismiss() }, confirmButton = {
        TextButton(onClick = { onDateSelected(selectedDate) }) {
            Text(text = "Confirm")
        }
    },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        }) {
        DatePicker(state = datePickerState)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseCategoryDropdown(listOfItems: List<String>, onItemSelected: (String) -> Unit){

    val expanded = remember {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = {
        expanded.value = it
    }){

        val selectedItem = remember {
            mutableStateOf<String>(listOfItems[0])
        }
        TextField(value = selectedItem.value, onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            }
            )
        ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = {  }) {

            listOfItems.forEach {
                DropdownMenuItem(text = { Text(text = it) }, onClick = {
                    selectedItem.value = it
                    onItemSelected(selectedItem.value)
                    expanded.value = false})
            }
            
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpenseTypeDropdown(listOfItems: List<String>, onItemSelected: (String) -> Unit){

    val expanded = remember {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = {
        expanded.value = it
    }){

        val selectedItem = remember {
            mutableStateOf<String>(listOfItems[0])
        }
        TextField(value = selectedItem.value, onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            }
            )
        ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = {  }) {

            listOfItems.forEach {
                DropdownMenuItem(text = { Text(text = it) }, onClick = {
                    selectedItem.value = it
                    onItemSelected(selectedItem.value)
                    expanded.value = false})
            }

        }
    }
}