package di

import data.repo.AuthRepo
import data.repo.DebtRepo
import data.repo.FriendsRepo
import domain.repo.IAuthRepo
import domain.repo.IDebtRepo
import domain.repo.IFriendsRepo
import domain.usecases.auth.GetAuthedUserUseCase
import domain.usecases.debts.GetAllDebtsUseCase
import domain.usecases.friends.GetFriendsListUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindProvider
import org.kodein.di.provider

object AppDi {
    private val repoModule = DI.Module("repos") {
        bind<IFriendsRepo>() with provider { FriendsRepo() }
        bind<IDebtRepo>() with provider { DebtRepo() }
        bind<IAuthRepo>() with provider { AuthRepo() }
    }

    private val useCaseModule = DI.Module("useCases") {
        bindProvider { GetAuthedUserUseCase() }
        bindProvider { GetFriendsListUseCase() }
        bindProvider { GetAllDebtsUseCase() }
    }

    val di = DI {
        import(useCaseModule)
        import(repoModule)
    }
}