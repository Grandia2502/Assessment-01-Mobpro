package com.grandiamuhammad3096.assessment01.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.grandiamuhammad3096.assessment01.R
import com.grandiamuhammad3096.assessment01.ui.theme.Assessment01Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { innerPadding ->
        KonversiSuhu(Modifier.padding(innerPadding))
    }
}

@Composable
fun KonversiSuhu(modifier: Modifier = Modifier) {
    var inputNilaiSuhu by rememberSaveable { mutableStateOf("") }
    var selectedUnit by rememberSaveable { mutableStateOf("Celsius") }
    val units = listOf("Celsius", "Fahrenheit", "Kelvin", "Reamur")

    val hasilKonversi = konversiSuhu(inputNilaiSuhu, selectedUnit)

    Column(
        modifier = modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.judul_aplikasi),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = inputNilaiSuhu,
            onValueChange = { inputNilaiSuhu = it },
            label = { Text(text = stringResource(R.string.keterangan_label_suhu)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

//      Dropdown pilihan satuan suhu
        PilihanSatuanSuhu(
            options = units,
            selected = selectedUnit,
            onOptionSelected = { selectedUnit = it },
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hasil Konversi
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = stringResource(R.string.hasil_konversi),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp)
                hasilKonversi.forEach { (satuan, nilai) ->
                    Text("$satuan: $nilai", fontSize = 16.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PilihanSatuanSuhu(
    options: List<String>,
    selected: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(text = stringResource(R.string.pilih_satuan_suhu)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { label ->
                DropdownMenuItem(
                    text = { Text(label) },
                    onClick = {
                        onOptionSelected(label)
                        expanded = false
                    }
                )
            }
        }
    }
}

fun konversiSuhu(input: String, fromUnit: String): Map<String, String> {
    val suhu = input.toDoubleOrNull() ?: return emptyMap()
    val celsius = when (fromUnit) {
        "Celsius" -> suhu
        "Fahrenheit" -> (suhu - 32) * 5 / 9
        "Kelvin" -> suhu - 273.15
        "Reamur" -> suhu * 5 / 4
        else -> suhu
    }

    return mapOf(
        "Celsius" to "%.2f".format(celsius),
        "Fahrenheit" to "%.2f".format(celsius * 9 / 5 + 32),
        "Kelvin" to "%.2f".format(celsius + 273.15),
        "Reamur" to "%.2f".format(celsius * 4 / 5)
    )
}

@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL, showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Assessment01Theme {
        MainScreen()
    }
}