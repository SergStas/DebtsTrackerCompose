package domain.repo

import domain.models.AuthArgs
import domain.models.AuthTokens
import domain.models.User

interface IAuthRepo {
    suspend fun login(authArgs: AuthArgs): AuthResult.Login

    suspend fun register(authArgs: AuthArgs): AuthResult.Register

    suspend fun getAuthedUser(): User?

    sealed interface AuthResult {
        sealed class Success(val tokens: AuthTokens): AuthResult
        data object UnknownError: AuthResult

        sealed interface Login: AuthResult {
            class Success(tokens: AuthTokens): Login, AuthResult.Success(tokens)
            data object Invalid: Login
        }

        sealed interface Register: AuthResult {
            class Success(tokens: AuthTokens): Register, AuthResult.Success(tokens)
            data object UsernameIsTaken: Register
            data object UsernameIsInvalid: Register
            data object PasswordIsInvalid: Register
        }
    }
}