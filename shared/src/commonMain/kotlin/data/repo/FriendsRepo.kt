package data.repo

import domain.models.Friend
import domain.repo.IFriendsRepo

class FriendsRepo: IFriendsRepo {
    private val list = mutableListOf(
        Friend("1", "aboba", null, false),
        Friend("2", "dedushka", null, true),
        Friend("3", "pena", null, false),
    )

    override suspend fun getAll() =
        list.toList()

    override suspend fun getById(id: String) =
        list.firstOrNull { it.userId == id }

    override suspend fun create(user: Friend) =
        list.add(user)
}