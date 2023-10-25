package domain.usecases.debts

import di.AppDiAware
import domain.repo.IDebtRepo
import domain.models.Debt
import org.kodein.di.instance

class CreateDebtUseCase: AppDiAware {
    private val repo by instance <IDebtRepo>()

    suspend operator fun invoke(debt: Debt) {
        repo.create(debt)
    }
}