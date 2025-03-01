package com.afra.pennypie.presentation.transactions.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afra.pennypie.domain.model.Account
import com.afra.pennypie.domain.model.Category
import com.afra.pennypie.domain.model.Transaction
import com.afra.pennypie.domain.model.TransactionType
import com.afra.pennypie.domain.usecase.account.GetAccountsUseCase
import com.afra.pennypie.domain.usecase.category.GetCategoriesUseCase
import com.afra.pennypie.domain.usecase.transaction.AddTransactionUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime

data class AddTransactionState(
    val amount: String = "",
    val type: TransactionType = TransactionType.EXPENSE,
    val selectedCategory: Category? = null,
    val selectedAccount: Account? = null,
    val note: String = "",
    val date: LocalDateTime = LocalDateTime.now(),
    val categories: List<Category> = emptyList(),
    val accounts: List<Account> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

sealed class AddTransactionEvent {
    data class AmountChanged(val amount: String) : AddTransactionEvent()
    data class TypeChanged(val type: TransactionType) : AddTransactionEvent()
    data class CategorySelected(val category: Category) : AddTransactionEvent()
    data class AccountSelected(val account: Account) : AddTransactionEvent()
    data class NoteChanged(val note: String) : AddTransactionEvent()
    data class DateChanged(val date: LocalDateTime) : AddTransactionEvent()
    object Submit : AddTransactionEvent()
}

class AddTransactionViewModel(
    private val addTransactionUseCase: AddTransactionUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddTransactionState())
    val state: StateFlow<AddTransactionState> = _state

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                combine(
                    getAccountsUseCase(),
                    getCategoriesUseCase.getAll()
                ) { accounts, categories ->
                    _state.update { currentState ->
                        currentState.copy(
                            accounts = accounts,
                            categories = categories
                        )
                    }
                }.collect()
            } catch (e: Exception) {
                _state.update { currentState ->
                    currentState.copy(error = e.message)
                }
            }
        }
    }

    fun onEvent(event: AddTransactionEvent) {
        when (event) {
            is AddTransactionEvent.AmountChanged -> {
                _state.update { it.copy(amount = event.amount) }
            }
            is AddTransactionEvent.TypeChanged -> {
                _state.update { it.copy(
                    type = event.type,
                    selectedCategory = null // Reset category when type changes
                ) }
            }
            is AddTransactionEvent.CategorySelected -> {
                _state.update { it.copy(selectedCategory = event.category) }
            }
            is AddTransactionEvent.AccountSelected -> {
                _state.update { it.copy(selectedAccount = event.account) }
            }
            is AddTransactionEvent.NoteChanged -> {
                _state.update { it.copy(note = event.note) }
            }
            is AddTransactionEvent.DateChanged -> {
                _state.update { it.copy(date = event.date) }
            }
            is AddTransactionEvent.Submit -> {
                submitTransaction()
            }
        }
    }

    private fun submitTransaction() {
        val currentState = _state.value

        // Validate input
        if (currentState.amount.isBlank()) {
            _state.update { it.copy(error = "Amount is required") }
            return
        }

        val amount = currentState.amount.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            _state.update { it.copy(error = "Invalid amount") }
            return
        }

        if (currentState.selectedCategory == null) {
            _state.update { it.copy(error = "Category is required") }
            return
        }

        if (currentState.selectedAccount == null) {
            _state.update { it.copy(error = "Account is required") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val transaction = Transaction(
                    id = 0, // Room will generate the ID
                    amount = amount,
                    type = currentState.type,
                    category = currentState.selectedCategory,
                    account = currentState.selectedAccount,
                    note = currentState.note,
                    date = currentState.date
                )
                addTransactionUseCase(transaction)
                _state.update { it.copy(isSuccess = true) }
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
} 