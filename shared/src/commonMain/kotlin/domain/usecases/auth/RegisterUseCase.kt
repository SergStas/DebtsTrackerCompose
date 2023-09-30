package domain.usecases.auth

import domain.models.AuthArgs
import domain.repo.IAuthRepo

class RegisterUseCase(
    private val authRepo: IAuthRepo,
) {
    suspend operator fun invoke(username: String, password: String) =
        authRepo.register(AuthArgs(username, password))
}