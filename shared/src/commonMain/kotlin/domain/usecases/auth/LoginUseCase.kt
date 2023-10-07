package domain.usecases.auth

import di.AppDiAware
import domain.models.AuthArgs
import domain.repo.IAuthRepo
import org.kodein.di.instance

class LoginUseCase : AppDiAware {
    private val authRepo by instance<IAuthRepo>()

    suspend operator fun invoke(username: String, password: String) =
        authRepo.login(AuthArgs(username, password))
}