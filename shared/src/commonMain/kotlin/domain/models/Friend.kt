package domain.models

data class Friend(
    val userId: String,
    val username: String,
    val fullName: String?,
    val isReal: Boolean,
)
