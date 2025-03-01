package com.afra.pennypie.presentation.transactions.add

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.afra.pennypie.R
import com.afra.pennypie.domain.model.Account
import com.afra.pennypie.domain.model.Category
import com.afra.pennypie.domain.model.TransactionType
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    navController: NavController,
    viewModel: AddTransactionViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    var showCategoryDropdown by remember { mutableStateOf(false) }
    var showAccountDropdown by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.popBackStack()
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = state.date.toInstant(java.time.ZoneOffset.UTC).toEpochMilli(),
            initialDisplayMode = DisplayMode.Input
        )
        
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val localDateTime = LocalDateTime.ofInstant(
                            java.time.Instant.ofEpochMilli(millis),
                            java.time.ZoneId.systemDefault()
                        )
                        viewModel.onEvent(AddTransactionEvent.DateChanged(localDateTime))
                    }
                    showDatePicker = false
                }) {
                    Text(stringResource(R.string.save))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.nav_back))
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Amount input
            OutlinedTextField(
                value = state.amount,
                onValueChange = { viewModel.onEvent(AddTransactionEvent.AmountChanged(it)) },
                label = { Text(stringResource(R.string.amount)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            // Transaction type selector
            TransactionTypeSelector(
                selectedType = state.type,
                onTypeSelected = { viewModel.onEvent(AddTransactionEvent.TypeChanged(it)) }
            )

            // Category selector
            ExposedDropdownMenuBox(
                expanded = showCategoryDropdown,
                onExpandedChange = { showCategoryDropdown = it }
            ) {
                OutlinedTextField(
                    value = state.selectedCategory?.name ?: "",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text(stringResource(R.string.category)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showCategoryDropdown) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = showCategoryDropdown,
                    onDismissRequest = { showCategoryDropdown = false }
                ) {
                    state.categories
                        .filter { it.type == state.type }
                        .forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    viewModel.onEvent(AddTransactionEvent.CategorySelected(category))
                                    showCategoryDropdown = false
                                }
                            )
                        }
                }
            }

            // Account selector
            ExposedDropdownMenuBox(
                expanded = showAccountDropdown,
                onExpandedChange = { showAccountDropdown = it }
            ) {
                OutlinedTextField(
                    value = state.selectedAccount?.name ?: "",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text(stringResource(R.string.account)) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = showAccountDropdown) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = showAccountDropdown,
                    onDismissRequest = { showAccountDropdown = false }
                ) {
                    state.accounts.forEach { account ->
                        DropdownMenuItem(
                            text = { Text(account.name) },
                            onClick = {
                                viewModel.onEvent(AddTransactionEvent.AccountSelected(account))
                                showAccountDropdown = false
                            }
                        )
                    }
                }
            }

            // Note input
            OutlinedTextField(
                value = state.note,
                onValueChange = { viewModel.onEvent(AddTransactionEvent.NoteChanged(it)) },
                label = { Text(stringResource(R.string.note)) },
                modifier = Modifier.fillMaxWidth()
            )

            // Date picker
            OutlinedTextField(
                value = state.date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                onValueChange = { },
                readOnly = true,
                label = { Text(stringResource(R.string.date)) },
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.DateRange, contentDescription = stringResource(R.string.date))
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (state.error != null) {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = { viewModel.onEvent(AddTransactionEvent.Submit) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                Text(stringResource(R.string.add))
            }
        }
    }
}

@Composable
private fun TransactionTypeSelector(
    selectedType: TransactionType,
    onTypeSelected: (TransactionType) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TransactionType.values().forEach { type ->
            FilterChip(
                selected = type == selectedType,
                onClick = { onTypeSelected(type) },
                label = {
                    Text(
                        stringResource(
                            when (type) {
                                TransactionType.INCOME -> R.string.income
                                TransactionType.EXPENSE -> R.string.expense
                                TransactionType.TRANSFER -> R.string.transfer
                            }
                        )
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
} 