package domain.usecases.friends

import di.AppDiAware
import domain.repo.IFriendsRepo
import org.kodein.di.instance

class GetFriendsListUseCase: AppDiAware {
    private val friendsRepo by instance<IFriendsRepo>()

    suspend operator fun invoke() =
        friendsRepo.getAll()
}