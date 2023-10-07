package domain.usecases.debts

import di.AppDiAware
import domain.models.Currency
import domain.models.User
import domain.repo.IDebtRepo
import org.kodein.di.instance

class GetAllDebtsUseCase: AppDiAware {
    private val debtRepo by instance<IDebtRepo>()

    suspend operator fun invoke(owner: User, args: FilterArgs? = null) =
        debtRepo.getAll(owner, args)

    data class FilterArgs(
        val users: List<User>? = null,
        val types: List<DebtTag>? = null,
        val currencies: List<Currency>? = null,
    ) {
        enum class DebtTag {
            Active, Accepted, ToPay, ToReceive, PendingConfirm, Declined;
        }
    }
}