package domain.usecases.debts

import domain.repo.IDebtRepo
import domain.models.Debt

class CreateDebtUseCase(
    private val repo: IDebtRepo,
) {
    suspend operator fun invoke(debt: Debt) {
        repo.create(debt)
    }
}