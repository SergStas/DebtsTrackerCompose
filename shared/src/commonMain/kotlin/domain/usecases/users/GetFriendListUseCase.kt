package domain.usecases.users

import domain.models.Friend
import domain.repo.IAuthRepo
import domain.repo.IFriendsRepo

class GetFriendListUseCase(
    private val friendsRepo: IFriendsRepo,
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke(): List<Friend> {
        val authedUser = authRepo.getAuthedUser() ?: return emptyList()
        return friendsRepo.getAll().filter { it.userId != authedUser.userId }
    }
}