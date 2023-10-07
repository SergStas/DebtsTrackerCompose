package domain.repo

import domain.models.User

interface IFriendsRepo {
    suspend fun getAll(): List<User>

    suspend fun getById(id: String): User?

    suspend fun create(user: User): Boolean
}