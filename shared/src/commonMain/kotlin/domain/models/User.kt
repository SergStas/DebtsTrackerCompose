package domain.models

data class User(
    val userId: String,
    val username: String,
    val fullName: String?,
    val isReal: Boolean,
)
