package domain.usecases.friends

import domain.repo.IFriendsRepo

class GetFriendsListUseCase(
    private val friendsRepo: IFriendsRepo,
) {
    suspend operator fun invoke() =
        friendsRepo.getAll()
}