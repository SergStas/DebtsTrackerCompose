package domain.models

data class AuthTokens(
    val token: String,
    val refreshToken: String,
)
