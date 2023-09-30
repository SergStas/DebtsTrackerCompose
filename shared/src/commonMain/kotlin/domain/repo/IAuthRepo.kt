package domain.repo

import domain.models.AuthArgs
import domain.models.AuthTokens
import domain.models.Friend

interface IAuthRepo {
    suspend fun login(authArgs: AuthArgs): AuthResult.Login

    suspend fun register(authArgs: AuthArgs): AuthResult.Register

    suspend fun getAuthedUser(): Friend?

    sealed interface AuthResult {
        sealed class Success(val tokens: AuthTokens): AuthResult
        object UnknownError: AuthResult

        sealed interface Login: AuthResult {
            class Success(tokens: AuthTokens): Login, AuthResult.Success(tokens)
            object Invalid: Login
        }

        sealed interface Register: AuthResult {
            class Success(tokens: AuthTokens): Register, AuthResult.Success(tokens)
            object UsernameIsTaken: Register
            object UsernameIsInvalid: Register
            object PasswordIsInvalid: Register
        }
    }
}