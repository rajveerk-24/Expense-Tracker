package com.sarrafenterprises.expensetracker.Screen

import java.text.SimpleDateFormat
import java.util.Locale

object Utils {

    fun formatDateToHumanReadableFormat(
        dateMillis: Long
    ) : String {
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormatter.format(dateMillis)
    }
}
