package com.afra.pennypie.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afra.pennypie.domain.model.Account
import com.afra.pennypie.domain.model.Transaction
import com.afra.pennypie.domain.model.TransactionType
import com.afra.pennypie.domain.usecase.account.GetAccountsUseCase
import com.afra.pennypie.domain.usecase.transaction.GetTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime

data class HomeState(
    val totalBalance: Double = 0.0,
    val accounts: List<Account> = emptyList(),
    val recentTransactions: List<Transaction> = emptyList(),
    val monthlyIncome: Double = 0.0,
    val monthlyExpenses: Double = 0.0,
    val isLoading: Boolean = true
)

class HomeViewModel(
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = combine(
        getAccountsUseCase(),
        getTransactionsUseCase.getAll(),
        getMonthlyIncome(),
        getMonthlyExpenses()
    ) { accounts, transactions, monthlyIncome, monthlyExpenses ->
        HomeState(
            totalBalance = accounts.sumOf { it.balance },
            accounts = accounts,
            recentTransactions = transactions.take(5),
            monthlyIncome = monthlyIncome,
            monthlyExpenses = monthlyExpenses,
            isLoading = false
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState()
    )

    private fun getMonthlyIncome() = getTransactionsUseCase.getTotalAmountByTypeAndPeriod(
        type = TransactionType.INCOME,
        startDate = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0),
        endDate = LocalDateTime.now()
    )

    private fun getMonthlyExpenses() = getTransactionsUseCase.getTotalAmountByTypeAndPeriod(
        type = TransactionType.EXPENSE,
        startDate = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0),
        endDate = LocalDateTime.now()
    )
} 