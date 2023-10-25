package composables.screens.create

import composables.screens.create.models.DebtDirection
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import di.AppDiAware
import domain.models.Currency
import domain.models.Debt
import domain.models.User
import domain.usecases.auth.GetAuthedUserUseCase
import domain.usecases.currencies.GetCurrencyListUseCase
import domain.usecases.debts.CreateDebtUseCase
import domain.usecases.friends.GetFriendListUseCase
import io.ktor.util.date.GMTDate
import io.ktor.util.date.Month
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance
import kotlin.math.absoluteValue

class CreateNewDebtViewModel: ViewModel(), AppDiAware {
    private val getFriendsList by instance<GetFriendListUseCase>()
    private val getCurrencies by instance<GetCurrencyListUseCase>()
    private val getAuthedUser by instance<GetAuthedUserUseCase>()
    private val createDebt by instance<CreateDebtUseCase>()

    val loading get() = _loading.asStateFlow()
    private val _loading = MutableStateFlow(false)

    val validationResult get() = _validationResult.asStateFlow()
    private val _validationResult = MutableStateFlow(ValidationResult.Waiting)

    val debtDirection get() = _debtDirection.asStateFlow()
    private val _debtDirection = MutableStateFlow(DebtDirection.ToReceive)

    val friendsList get() = _friendsList.asStateFlow()
    private val _friendsList = MutableStateFlow<List<User>>(emptyList())

    val client get() = _client.asStateFlow()
    private val _client = MutableStateFlow<User?>(null)

    val currency get() = _currency.asStateFlow()
    private val _currency = MutableStateFlow<Currency?>(null)

    val currencies get() = _currencies.asStateFlow()
    private val _currencies = MutableStateFlow<List<Currency>>(emptyList())

    val sum get() = _sum.asStateFlow()
    private var _sum = MutableStateFlow(0.0)

    val desc get() = _desc.asStateFlow()
    private var _desc = MutableStateFlow("")

    val descEnabled get() = _descEnabled.asStateFlow()
    private var _descEnabled = MutableStateFlow(false)

    val expirationDate get() = _expirationDate.asStateFlow()
    private var _expirationDate = MutableStateFlow(0L)

    val hasExpirationDate get() = _hasExpirationDate.asStateFlow()
    private var _hasExpirationDate = MutableStateFlow(false)

    fun submit() {
        viewModelScope.launch {
            val result = try {
                val user = getAuthedUser()
                val client = client.value
                val currency = _currency.value
                val sum = _sum.value
                val expirationDate = _expirationDate.value
                    .takeIf { _hasExpirationDate.value }
                    ?.takeIf { it != 0L }
                val description = _desc.value
                    .takeIf { _descEnabled.value }
                    ?.takeIf { it.isNotEmpty() }
                val status = if (client?.isReal == true) Debt.Status.ASSIGNED else Debt.Status.ACCEPTED
                when {
                    user == null -> ValidationResult.UnknownError
                    client == null -> ValidationResult.ClientUnspecified
                    currency == null -> ValidationResult.CurrencyUnspecified
                    sum.absoluteValue < 1e-3 -> ValidationResult.SumIsZero
                    sum < 0 -> ValidationResult.SumIsNegative
                    _descEnabled.value && description == null -> ValidationResult.DescriptionUnspecified
                    _hasExpirationDate.value && expirationDate == null -> ValidationResult.ExpirationDateUnspecified
                    else -> {
                        val debt = Debt(
                            lender = if (debtDirection.value == DebtDirection.ToPay) client else user,
                            borrower = if (debtDirection.value == DebtDirection.ToPay) user else client,
                            currency = currency,
                            sum = sum,
                            creationDate = GMTDate().timestamp,
                            expirationDate = expirationDate,
                            description = description,
                            status = status,
                        )
                        createDebt(debt)
                        ValidationResult.Success
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                ValidationResult.UnknownError
            }
            _validationResult.value = result
        }
    }

    fun updateDate(token: String) {
        viewModelScope.launch {
            val timestamp = try {
                GMTDate(
                    seconds = 0,
                    minutes = 0,
                    hours = 12,
                    dayOfMonth = token.substring((0..1)).toInt(),
                    month = Month.from(token.substring((3..4)).toInt() - 1),
                    year = token.substringAfterLast("-").toInt(),
                )
            } catch (_: Exception) { return@launch }
            _expirationDate.value = timestamp.timestamp
        }
    }

    fun enableExpirationDate(value: Boolean) {
        viewModelScope.launch {
            _hasExpirationDate.value = value
        }
    }

    fun enableDesc(value: Boolean) {
        viewModelScope.launch {
            _descEnabled.value = value
        }
    }

    fun updateDesc(value: String) {
        viewModelScope.launch {
            _desc.value = value
        }
    }

    fun updateSum(value: Double) {
        viewModelScope.launch {
            _sum.value = value
        }
    }

    fun updateClient(user: User) {
        viewModelScope.launch {
            _client.value = user
        }
    }

    fun updateCurrency(currency: Currency) {
        _currency.value = currency
    }

    fun switchDirection() {
        viewModelScope.launch {
            _debtDirection.value = _debtDirection.value.switch()
        }
    }

    fun loadLists() {
        viewModelScope.launch {
            _loading.value = true
            _friendsList.value = getFriendsList()
            _currencies.value = getCurrencies()
            _friendsList.value.firstOrNull()?.let { updateClient(it) }
            _currencies.value.firstOrNull()?.let { updateCurrency(it) }
            _loading.value = false
        }
    }

    enum class ValidationResult {
        Waiting,
        Success,
        UnknownError,
        ClientUnspecified,
        CurrencyUnspecified,
        SumIsNegative,
        DescriptionUnspecified,
        ExpirationDateUnspecified,
        SumIsZero,
    }
}