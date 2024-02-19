package com.mexiti.costogasolina

import android.os.Bundle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.mexiti.costogasolina.ui.theme.CostoGasolinaTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CostoGasolinaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                    CostGasLayout()
                }
            }
        }
    }
}

@Composable
fun CostGasLayout() {
    var precioLitro by remember{
        mutableStateOf("")
    }
    var cantLitros by remember {
        mutableStateOf("")
    }
    var propina by remember {
        mutableStateOf("")
    }
    var estadoSwitch by remember {
        mutableStateOf(false)
    }

    val precio=precioLitro.toDoubleOrNull()?:0.0
    val cantidad=cantLitros.toDoubleOrNull()?:0.0
    val tip=propina.toDoubleOrNull()?:0.0
    val total=CalcularMonto(precio,cantidad, tip)

    Column (
        modifier=Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ){
        Text(
            text = stringResource(R.string.calcular_monto),
            fontWeight = FontWeight.Bold
            )
       EditNumberField(
           label = R.string.ingresa_gasolina,
           leadingIcon = R.drawable.money_gas ,
           keyboardsOptions = KeyboardOptions.Default.copy(
               keyboardType = KeyboardType.Number,
               imeAction = ImeAction.Next
           ),
           value = precioLitro,
           onValueChanged = {precioLitro=it},
           modifier=Modifier.fillMaxWidth()
       )
        EditNumberField(
            label = R.string.ingresa_cantidad,
            leadingIcon = R.drawable.cant_gas ,
            keyboardsOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            value = cantLitros,
            onValueChanged = {cantLitros=it},
            modifier=Modifier.fillMaxWidth()
        )

        if(estadoSwitch){
            EditNumberField(
                label = R.string.propina,
                leadingIcon = R.drawable.money_tip,
                keyboardsOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                value = propina,
                onValueChanged = {propina = it},
                modifier = Modifier.fillMaxWidth()
            )

        }


        Row{

            Text(
                text = "¿Desea agregar la propina?",
                textAlign = TextAlign.Center
            )

            Switch(
                checked = estadoSwitch,
                onCheckedChange =  {isChecked ->
                    estadoSwitch = isChecked
                }
            )
        }

        Text(
            text = "Total:  $total",
            fontWeight = FontWeight.Bold
        )

    }

}
private fun CalcularMonto(precio:Double,cantLitros:Double,propina:Double):String{
    val total=(precio*cantLitros)+propina

    return NumberFormat.getCurrencyInstance().format(total)
}

@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardsOptions:KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        label = { Text(text = stringResource(id = label))  },
        value = value,
        singleLine = true,
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon) , contentDescription = null) },
        keyboardOptions = keyboardsOptions,
        modifier = modifier,
        onValueChange = onValueChanged
    )

}

@Preview(showBackground = true)
@Composable
fun CostGasLayoutPreview() {
    CostoGasolinaTheme {
        CostGasLayout()
    }
}