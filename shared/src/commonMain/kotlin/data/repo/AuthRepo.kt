package data.repo

import di.AppDiAware
import domain.models.AuthArgs
import domain.models.AuthTokens
import domain.repo.IAuthRepo

class AuthRepo: AppDiAware, IAuthRepo {
    override suspend fun getAuthedUser() =
        FriendsRepo.me

    override suspend fun login(authArgs: AuthArgs) =
        IAuthRepo.AuthResult.Login.Success(AuthTokens("", ""))

    override suspend fun register(authArgs: AuthArgs) =
        IAuthRepo.AuthResult.Register.Success(AuthTokens("", ""))
}