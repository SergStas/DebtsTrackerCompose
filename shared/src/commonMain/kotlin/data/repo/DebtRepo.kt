package data.repo

import di.AppDiAware
import domain.models.Currency
import domain.models.Debt
import domain.models.User
import domain.repo.IDebtRepo
import domain.usecases.debts.GetAllDebtsUseCase
import io.ktor.util.date.GMTDate
import io.ktor.util.date.Month

class DebtRepo: IDebtRepo, AppDiAware {
    override suspend fun create(debt: Debt) {

    }

    override suspend fun getAll(owner: User, args: GetAllDebtsUseCase.FilterArgs?) =
        listOf(
            Debt(
                FriendsRepo.me,
                FriendsRepo.user2,
                Currency.Gel,
                3220.0,
                GMTDate(59, 59, 23, 11, Month.SEPTEMBER, 2009).timestamp,
                null,
                null,
                Debt.Status.ASSIGNED,
            ),
            Debt(
                FriendsRepo.user3,
                FriendsRepo.me,
                Currency.Rub,
                1000.0,
                GMTDate(59, 59, 23, 29, Month.SEPTEMBER, 2023).timestamp,
                null,
                "Пиво и чипсы",
                Debt.Status.ACCEPTED,
            ),
            Debt(
                FriendsRepo.user3,
                FriendsRepo.me,
                Currency.Rub,
                1500.0,
                GMTDate(59, 59, 23, 3, Month.OCTOBER, 2023).timestamp,
                GMTDate(59, 59, 23, 30, Month.OCTOBER, 2023).timestamp,
                "Чипсы и пиво",
                Debt.Status.ASSIGNED,
            ),
            Debt(
                FriendsRepo.me,
                FriendsRepo.user3,
                Currency.Rub,
                400.0,
                GMTDate(59, 59, 23, 3, Month.OCTOBER, 2023).timestamp,
                GMTDate(59, 59, 23, 13, Month.OCTOBER, 2023).timestamp,
                "Такса",
                Debt.Status.ASSIGNED,
            ),
        )
}