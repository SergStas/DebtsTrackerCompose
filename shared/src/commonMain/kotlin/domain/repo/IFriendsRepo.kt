package domain.repo

import domain.models.Friend

interface IFriendsRepo {
    suspend fun getAll(): List<Friend>

    suspend fun getById(id: String): Friend?

    suspend fun create(user: Friend): Boolean
}