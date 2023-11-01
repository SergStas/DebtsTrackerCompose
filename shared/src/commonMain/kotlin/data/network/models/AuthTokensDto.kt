package data.network.models

import domain.models.AuthTokens

data class AuthTokensDto(
    val token: String,
    val refresh_token: String,
) {
    fun toDomainModel() = AuthTokens(token, refresh_token)
}
