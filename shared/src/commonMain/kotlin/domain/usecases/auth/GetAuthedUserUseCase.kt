package domain.usecases.auth

import domain.models.Friend
import domain.repo.IAuthRepo

class GetAuthedUserUseCase(
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke(): Friend? =
        authRepo.getAuthedUser()
}