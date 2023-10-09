package data.repo

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import di.AppDiAware
import domain.models.AuthArgs
import domain.models.AuthTokens
import domain.models.User
import domain.repo.IAuthRepo
import org.kodein.di.instance

class AuthRepo: AppDiAware, IAuthRepo {
    companion object {
        private const val USER_ID_KEY = "auth_userId"
        private const val USERNAME_KEY = "auth_username"
        private const val FULL_NAME_KEY = "auth_fullName"
    }

    private val settings by instance<Settings>()

    override suspend fun getAuthedUser(): User? {
        return User(
            userId = settings.getStringOrNull(USER_ID_KEY) ?: return null,
            username = settings.getStringOrNull(USERNAME_KEY) ?: return null,
            fullName = settings.getStringOrNull(FULL_NAME_KEY),
            isReal = true,
        )
    }

    override suspend fun login(authArgs: AuthArgs): IAuthRepo.AuthResult.Login.Success {
        settings[USER_ID_KEY] = authArgs.username
        settings[USERNAME_KEY] = authArgs.username
        return IAuthRepo.AuthResult.Login.Success(AuthTokens("", ""))
    }

    override suspend fun register(authArgs: AuthArgs): IAuthRepo.AuthResult.Register.Success {
        settings[USER_ID_KEY] = authArgs.username
        settings[USERNAME_KEY] = authArgs.username
        return IAuthRepo.AuthResult.Register.Success(AuthTokens("", ""))
    }
}