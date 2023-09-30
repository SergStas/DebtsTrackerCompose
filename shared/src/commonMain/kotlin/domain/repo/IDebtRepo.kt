package domain.repo

import domain.models.Debt
import domain.usecases.debts.GetAllDebtsUseCase
import domain.models.Friend

interface IDebtRepo {
    suspend fun create(debt: Debt)

    suspend fun getAll(owner: Friend, args: GetAllDebtsUseCase.FilterArgs? = null): List<Debt>
}