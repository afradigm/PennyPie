package com.afra.pennypie.presentation.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afra.pennypie.domain.model.Transaction
import com.afra.pennypie.domain.model.TransactionType
import com.afra.pennypie.domain.usecase.transaction.GetTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime

data class TransactionsState(
    val transactions: List<Transaction> = emptyList(),
    val selectedType: TransactionType? = null,
    val startDate: LocalDateTime = LocalDateTime.now().withDayOfMonth(1),
    val endDate: LocalDateTime = LocalDateTime.now(),
    val isLoading: Boolean = true
)

sealed class TransactionsEvent {
    data class SelectType(val type: TransactionType?) : TransactionsEvent()
    data class SetDateRange(val start: LocalDateTime, val end: LocalDateTime) : TransactionsEvent()
}

class TransactionsViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TransactionsState())
    val state: StateFlow<TransactionsState> = _state

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        val currentState = _state.value
        val flow = when (currentState.selectedType) {
            null -> getTransactionsUseCase.getAll()
            else -> getTransactionsUseCase.getByType(currentState.selectedType)
        }

        flow.map { transactions ->
            transactions.filter { transaction ->
                transaction.date.isAfter(currentState.startDate) &&
                transaction.date.isBefore(currentState.endDate.plusDays(1))
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun onEvent(event: TransactionsEvent) {
        when (event) {
            is TransactionsEvent.SelectType -> {
                _state.value = _state.value.copy(selectedType = event.type)
                loadTransactions()
            }
            is TransactionsEvent.SetDateRange -> {
                _state.value = _state.value.copy(
                    startDate = event.start,
                    endDate = event.end
                )
                loadTransactions()
            }
        }
    }
} 