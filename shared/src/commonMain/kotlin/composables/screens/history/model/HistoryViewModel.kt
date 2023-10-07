package composables.screens.history.model

import data.network.avatarUrl
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import di.AppDiAware
import domain.models.Debt
import domain.models.User
import domain.usecases.auth.GetAuthedUserUseCase
import domain.usecases.debts.GetAllDebtsUseCase
import io.ktor.util.date.GMTDate
import io.ktor.util.date.minus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance
import res.StringResources
import util.extensions.format

class HistoryViewModel: AppDiAware, ViewModel() {
    private val getAllDebtsUseCase by instance<GetAllDebtsUseCase>()
    private val getAuthedUserUseCase by instance<GetAuthedUserUseCase>()

    val debtList get() = _debtsList.asStateFlow()
    private val _debtsList = MutableStateFlow<List<DebtHistoryItem>>(emptyList())

    val loading get() = _loading.asStateFlow()
    private val _loading = MutableStateFlow(false)

    fun loadContent() {
        viewModelScope.launch {
            _loading.emit(true)
            val user = getAuthedUserUseCase()
            user?.run {
                _debtsList.emit(
                    getAllDebtsUseCase(this).map { mapToItem(it, this) }
                )
            }
            _loading.emit(false)
        }
    }

    private fun mapToItem(debt: Debt, user: User) =
        DebtHistoryItem(
            origin = debt,
            sumDisplay = StringResources.get().debtSumPh
                .replaceFirst("%s", debt.sum.format(2))
                .replaceFirst("%s", debt.currency.name.uppercase()),
            avatarUrl = debt.secondUser(user).avatarUrl,
            directionDisplay = StringResources.get().run {
                if (debt.isIncoming(user)) debtLabelIncoming else debtLabelOutgoing
            },
            usernameDisplay = debt.secondUser(user).username,
            creationDateDisplay = GMTDate(debt.creationDate).run {
                "$dayOfMonth ${month.value} $year"
            },
            expirationDateDisplay = debt.expirationDate?.let {
                GMTDate(it).run { "$dayOfMonth ${month.value} $year" }
            },
            statusDisplay = debt.status.name,
            description = debt.description,
            overdueVisibility = debt.expirationDate?.let {
                it > GMTDate(null).timestamp
            } ?: false,
            warningVisibility = debt.expirationDate?.let {
                it > GMTDate(null).minus(1000L * 3600 * 24 * 7).timestamp
            } ?: false,
            isIncoming = debt.isIncoming(user),
            onClick = {},
        )
}