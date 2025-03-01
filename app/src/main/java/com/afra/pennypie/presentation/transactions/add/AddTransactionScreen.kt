package com.afra.pennypie.presentation.transactions.add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.afra.pennypie.domain.model.Account
import com.afra.pennypie.domain.model.Category
import com.afra.pennypie.domain.model.TransactionType
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    navController: NavController,
    viewModel: AddTransactionViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Transaction") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                label = { Text("Amount") },
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
                expanded = false,
                onExpandedChange = { }
            ) {
                OutlinedTextField(
                    value = state.selectedCategory?.name ?: "",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth()
                )

                DropdownMenu(
                    expanded = false,
                    onDismissRequest = { }
                ) {
                    state.categories
                        .filter { it.type == state.type }
                        .forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category.name) },
                                onClick = {
                                    viewModel.onEvent(AddTransactionEvent.CategorySelected(category))
                                }
                            )
                        }
                }
            }

            // Account selector
            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = { }
            ) {
                OutlinedTextField(
                    value = state.selectedAccount?.name ?: "",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Account") },
                    modifier = Modifier.fillMaxWidth()
                )

                DropdownMenu(
                    expanded = false,
                    onDismissRequest = { }
                ) {
                    state.accounts.forEach { account ->
                        DropdownMenuItem(
                            text = { Text(account.name) },
                            onClick = {
                                viewModel.onEvent(AddTransactionEvent.AccountSelected(account))
                            }
                        )
                    }
                }
            }

            // Note input
            OutlinedTextField(
                value = state.note,
                onValueChange = { viewModel.onEvent(AddTransactionEvent.NoteChanged(it)) },
                label = { Text("Note") },
                modifier = Modifier.fillMaxWidth()
            )

            // Date picker
            OutlinedTextField(
                value = state.date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                onValueChange = { },
                readOnly = true,
                label = { Text("Date") },
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = "Select date")
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (state.error != null) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = { viewModel.onEvent(AddTransactionEvent.Submit) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                Text("Add Transaction")
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
                        type.name.lowercase()
                            .replaceFirstChar { it.uppercase() }
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
} 