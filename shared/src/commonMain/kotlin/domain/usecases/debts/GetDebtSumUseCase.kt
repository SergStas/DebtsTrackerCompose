package domain.usecases.debts

import domain.repo.IAuthRepo
import domain.repo.IDebtRepo

class GetDebtSumUseCase(
    private val debtRepo: IDebtRepo,
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke(args: GetAllDebtsUseCase.FilterArgs? = null) =
        authRepo.getAuthedUser()?.let { user ->
            debtRepo.getAll(user, args).sumOf {
                if (it.lender.userId == user.userId) it.sum else it.sum.unaryMinus()
            }
        } ?: 0.0
}