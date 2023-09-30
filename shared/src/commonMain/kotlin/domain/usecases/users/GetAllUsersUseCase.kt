package domain.usecases.users

import domain.repo.IFriendsRepo

class GetAllUsersUseCase(
    private val usersRepo: IFriendsRepo,
) {
    suspend operator fun invoke() =
        usersRepo.getAll()
}