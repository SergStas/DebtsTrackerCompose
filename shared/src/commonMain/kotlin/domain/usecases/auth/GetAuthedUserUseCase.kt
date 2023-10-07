package domain.usecases.auth

import di.AppDiAware
import domain.models.User
import domain.repo.IAuthRepo
import org.kodein.di.instance

class GetAuthedUserUseCase: AppDiAware {
    private val authRepo by instance<IAuthRepo>()

    suspend operator fun invoke(): User? =
        authRepo.getAuthedUser()
}