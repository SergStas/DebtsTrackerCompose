package data.network.api

import data.network.models.AuthTokensDto
import data.network.models.RefreshTokenDto
import data.network.models.UserDto
import di.AppDiAware
import domain.models.AuthArgs
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import org.kodein.di.instance

class Api: AppDiAware {
    companion object {
        const val API_URL = "http://???"
    }

    val httpClient by instance<HttpClient>()

    class Friends: Endpoint("friends") {
        suspend fun getAll(filter: String? = null): List<UserDto> =
            _get(filter)

        suspend fun inviteFriend(id: String): Unit =
            _post(Unit, id)
    }

    class Users: Endpoint("users") {
        suspend fun getAll(filter: String? = null): AuthTokensDto =
            _get(filter)

        suspend fun register(args: AuthArgs): AuthTokensDto =
            _post(args)

        suspend fun login(args: AuthArgs): AuthTokensDto =
            _post(args, "login")

        suspend fun refresh(args: RefreshTokenDto): AuthTokensDto =
            _post(args, "refresh")
    }

    sealed class Endpoint(val path: String) {
        protected suspend inline fun<TArgs: Any?, reified TResult: Any?> _get(
            args: TArgs,
            subPath: String = "",
        ) = httpClient.get("$API_URL/$path/$subPath").body<TResult>()

        protected suspend inline fun<TArgs: Any?, reified TResult: Any?> _post(
            args: TArgs,
            subPath: String = "",
        ) = httpClient.post("$API_URL/$path/$subPath").body<TResult>()
    }
}