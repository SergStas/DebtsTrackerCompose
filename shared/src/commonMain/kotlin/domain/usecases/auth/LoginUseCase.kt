package domain.usecases.auth

import domain.models.AuthArgs
import domain.repo.IAuthRepo

class LoginUseCase(
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke(username: String, password: String) =
        authRepo.login(AuthArgs(username, password))
}