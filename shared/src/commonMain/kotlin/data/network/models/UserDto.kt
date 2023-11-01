package data.network.models

import domain.models.User

data class UserDto(
    val user_id: String,
    val username: String,
    val fullname: String?,
    val is_real: Boolean,
) {
    fun toDomainModel() =
        User(user_id, username, fullname, is_real)
}
