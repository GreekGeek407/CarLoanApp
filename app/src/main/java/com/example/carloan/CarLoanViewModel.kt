package com.example.carloan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CarLoanViewModel : ViewModel(){
    var purchasePrice by mutableStateOf("")
    var downPayment by mutableStateOf("")
    var interestRate by  mutableFloatStateOf(0f)
    var numMonths by mutableStateOf("36 months")
    var monthlyPayment: Double by mutableDoubleStateOf(0.0)
}