package domain.repo

import domain.models.Debt
import domain.usecases.debts.GetAllDebtsUseCase
import domain.models.User

interface IDebtRepo {
    suspend fun create(debt: Debt)

    suspend fun getAll(owner: User, args: GetAllDebtsUseCase.FilterArgs? = null): List<Debt>
}