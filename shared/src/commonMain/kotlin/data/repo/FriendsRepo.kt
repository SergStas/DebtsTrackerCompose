package data.repo

import domain.models.User
import domain.repo.IFriendsRepo

class FriendsRepo: IFriendsRepo {
    companion object {
        val me = User("1", "aboba", null, false)
        val user2 = User("2", "dedushka", null, true)
        val user3 = User("3", "pena", null, false)
    }

    private val list = mutableListOf(
        me,
        user2,
        user3,
    )

    override suspend fun getAll() =
        list.toList()

    override suspend fun getById(id: String) =
        list.firstOrNull { it.userId == id }

    override suspend fun create(user: User) =
        list.add(user)
}