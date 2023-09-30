package domain.usecases.debts

import domain.models.Currency
import domain.models.Friend
import domain.repo.IDebtRepo

class GetAllDebtsUseCase(
    private val debtRepo: IDebtRepo,
) {
    suspend operator fun invoke(owner: Friend, args: FilterArgs? = null) =
        debtRepo.getAll(owner, args)

    data class FilterArgs(
        val friends: List<Friend>? = null,
        val types: List<DebtTag>? = null,
        val currencies: List<Currency>? = null,
    ) {
        enum class DebtTag {
            Active, Accepted, ToPay, ToReceive, PendingConfirm, Declined;
        }
    }
}