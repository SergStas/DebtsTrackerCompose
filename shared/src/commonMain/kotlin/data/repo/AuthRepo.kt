@file:OptIn(ExperimentalCoroutinesApi::class)

package data.repo

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set
import data.network.api.Api
import di.AppDiAware
import domain.models.AuthArgs
import domain.models.User
import domain.repo.IAuthRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.withContext
import org.kodein.di.instance

class AuthRepo: AppDiAware, IAuthRepo {
    companion object {
        private const val USER_ID_KEY = "auth_userId"
        private const val USERNAME_KEY = "auth_username"
        private const val FULL_NAME_KEY = "auth_fullName"
    }

    private val settings by instance<Settings>()
    private val api by instance<Api.Users>()
    private val dispatcher = newFixedThreadPoolContext(20, "IO")

    override suspend fun getAuthedUser() =
        withContext(dispatcher) {
            User(
                userId = settings.getStringOrNull(USER_ID_KEY) ?: return@withContext null,
                username = settings.getStringOrNull(USERNAME_KEY) ?: return@withContext null,
                fullName = settings.getStringOrNull(FULL_NAME_KEY),
                isReal = true,
            )
        }

    override suspend fun login(authArgs: AuthArgs) =
        withContext(dispatcher) {
            try {
                settings[USER_ID_KEY] = authArgs.username
                settings[USERNAME_KEY] = authArgs.username
                val dto = api.login(authArgs)
                IAuthRepo.AuthResult.Login.Success(dto.toDomainModel())
            } catch (_: Exception) {
                IAuthRepo.AuthResult.UnknownError
            }
        }

    override suspend fun register(authArgs: AuthArgs) =
        withContext(dispatcher) {
            try {
                settings[USER_ID_KEY] = authArgs.username
                settings[USERNAME_KEY] = authArgs.username
                val dto = api.register(authArgs)
                IAuthRepo.AuthResult.Register.Success(dto.toDomainModel())
            } catch (_: Exception) {
                IAuthRepo.AuthResult.UnknownError
            }
        }
}