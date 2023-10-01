package di

import data.repo.FriendsRepo
import domain.repo.IFriendsRepo
import domain.usecases.friends.GetFriendsListUseCase
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.bindProvider
import org.kodein.di.provider

object AppDi {
    private val repoModule = DI.Module("repos") {
        bind<IFriendsRepo>() with provider { FriendsRepo() }
    }

    private val useCaseModule = DI.Module("useCases") {
        bindProvider { GetFriendsListUseCase() }
    }

    val di = DI {
        import(useCaseModule)
        import(repoModule)
    }
}