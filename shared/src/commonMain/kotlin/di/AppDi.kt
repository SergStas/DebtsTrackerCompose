package di

import data.repo.AuthRepo
import data.repo.DebtRepo
import data.repo.FriendsRepo
import domain.repo.IAuthRepo
import domain.repo.IDebtRepo
import domain.repo.IFriendsRepo
import domain.usecases.auth.GetAuthedUserUseCase
import domain.usecases.auth.LoginUseCase
import domain.usecases.auth.RegisterUseCase
import domain.usecases.currencies.GetCurrencyListUseCase
import domain.usecases.debts.GetAllDebtsUseCase
import domain.usecases.friends.GetFriendListUseCase
import getSettings
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.provider

object AppDi {
    private val repoModule = DI.Module("repos") {
        bind<IFriendsRepo>() with provider { FriendsRepo() }
        bind<IDebtRepo>() with provider { DebtRepo() }
        bind<IAuthRepo>() with provider { AuthRepo() }
    }

    private val useCaseModule = DI.Module("useCases") {
        bindProvider { GetAuthedUserUseCase() }
        bindProvider { GetFriendListUseCase() }
        bindProvider { GetAllDebtsUseCase() }
        bindProvider { LoginUseCase() }
        bindProvider { RegisterUseCase() }
        bindProvider { GetCurrencyListUseCase() }
    }

    private val dataModule = DI.Module("data") {
        bindSingleton { getSettings() }
    }

    val di = DI {
        import(useCaseModule)
        import(repoModule)
        import(dataModule)
    }
}