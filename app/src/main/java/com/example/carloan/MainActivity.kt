package com.example.carloan

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.widget.RadioGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carloan.ui.theme.CarLoanTheme
import java.text.Format

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarLoanTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CarLoanScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun CarLoanScreen(modifier: Modifier = Modifier) {
    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        CarLoanPortrait(modifier)
    } else {
        CarLoanLandscape(modifier)
    }
}

@Composable
fun CarLoanPortrait(modifier: Modifier = Modifier) {
    val promptSize = 20
    val loanLengthOptions = listOf("36 months", "48 months", "60 months", "72 months", "84 months")
    var purchasePrice by remember { mutableStateOf("") }
    var downPayment by remember {mutableStateOf("")}
    var interestRate by remember { mutableFloatStateOf(0f) }
    var numMonths by remember {mutableStateOf("36 months")}
    var monthlyPayment: Double by remember {mutableDoubleStateOf(0.0)}
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(R.drawable.blue_car),
            contentDescription = "Blue car",
            modifier = Modifier.padding(horizontal = 20.dp)

        )
        Text(
            text = "Car Loan Calculator",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        TextFieldRow("Purchase Price:", promptSize, purchasePrice, {purchasePrice=it})
        TextFieldRow("Down Payment Amount: ", promptSize, downPayment, {downPayment=it})

        Text(
            text = "Annual Interest Rate: " + String.format("%.2f", interestRate) + "%",
            fontSize = promptSize.sp,
            modifier = Modifier.padding(10.dp)
        )
        Slider(
            value = interestRate,
            onValueChange = {interestRate = it},
            valueRange = 0f..20f,
            modifier = Modifier.padding(horizontal = 30.dp)
        )

        RadioGroup(loanLengthOptions, promptSize, numMonths, {numMonths = it})
        Button(
            onClick = {monthlyPayment = calculateMonthlyPayment(
                purchasePrice.toFloat(),
                downPayment.toFloat(),
                interestRate,
                numMonths
            )
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp)
        ){
            Text("Calculate Monthly Payment")
        }
        Text(
            text = String.format("Monthly Payment: $%.2f", monthlyPayment),
            fontSize = promptSize.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CarLoanLandscape(modifier: Modifier = Modifier) {
    val promptSize = 30
    val loanLengthOptions = listOf("36 months", "48 months", "60 months", "72 months", "84 months")
    var purchasePrice by remember { mutableStateOf("") }
    var downPayment by remember {mutableStateOf("")}
    var interestRate by remember { mutableFloatStateOf(0f) }
    var numMonths by remember {mutableStateOf("36 months")}
    var monthlyPayment: Double by remember {mutableDoubleStateOf(0.0)}
    Row(
        //modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top

    ) {
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Text(
                text = "Car Loan Calculator",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                //textAlign = TextAlign.Center,
                //modifier = Modifier.fillMaxWidth()
            )
            TextFieldRow("Purchase Price:", promptSize, purchasePrice, {purchasePrice=it})
            TextFieldRow("Down Payment Amount: ", promptSize, downPayment, {downPayment=it})

            Text(
                text = "Annual Interest Rate: " + String.format("%.2f", interestRate) + "%",
                fontSize = promptSize.sp,
                modifier = Modifier.padding(10.dp)
            )
            Slider(
                value = interestRate,
                onValueChange = {interestRate = it},
                valueRange = 0f..20f,
                modifier = Modifier.padding(horizontal = 30.dp).size(height = 40.dp, width = 350.dp)
            )
            Button(
                onClick = {monthlyPayment = calculateMonthlyPayment(
                    purchasePrice.toFloat(),
                    downPayment.toFloat(),
                    interestRate,
                    numMonths
                )
                },
                modifier = Modifier.padding(horizontal = 30.dp)
            ){
                Text("Calculate Monthly Payment")
            }

        }
        Column(

        ) {
            RadioGroup(loanLengthOptions, promptSize, numMonths, {numMonths = it})
            Text(
                text = String.format("Monthly Payment: $%.2f", monthlyPayment),
                fontSize = promptSize.sp,
                textAlign = TextAlign.Center,
                //modifier = Modifier.fillMaxWidth()
            )
        }

    }
}
@Composable
fun TextFieldRow(prompt: String, size: Int, moneyValue: String, onChange: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = prompt,
            fontSize = size.sp,
            modifier = Modifier.padding(5.dp)
        )
        TextField(
            value = moneyValue,
            onValueChange = onChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.padding(start = 5.dp, end = 20.dp).width(100.dp)
        )
    }
}

@Composable
fun RadioGroup(
    radioOptions: List<String>,
    size: Int,
    selectedOption: String,
    onSelect: (String) -> Unit
){
    Column(
        modifier = Modifier.padding(start = 30.dp, end = 30.dp, top = 30.dp)
    ) {
        Text(
            text = "Length of Loan:",
            fontSize = size.sp,
            modifier = Modifier.padding(5.dp)
        )
        radioOptions.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.selectable(
                    selected = selectedOption == option,
                    onClick = {onSelect(option)},
                    role = Role.RadioButton
                )
            ){
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { onSelect(option) }
                )
                Text(
                    text = option,
                    fontSize = (size * 0.8).sp
                )
            }
        }

    }
}

fun calculateMonthlyPayment(
    purchasePrice: Float,
    downPayment: Float,
    apr: Float,
    numMonths: String
): Double{
    val mr = apr/1200
    val loanSize = purchasePrice - downPayment
    val loanLength = when (numMonths) {
        "36 months" -> 36
        "48 months" -> 48
        "60 months" -> 60
        "72 months" -> 72
        else -> 84
    }
    return mr * loanSize / (1-Math.pow((1+mr).toDouble(), ((loanLength * -1).toDouble())))
}