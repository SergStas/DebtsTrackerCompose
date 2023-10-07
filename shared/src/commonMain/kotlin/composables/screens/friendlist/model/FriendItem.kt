package composables.screens.friendlist.model

import domain.models.Currency
import domain.models.User

data class FriendItem(
    val user: User,
    val debts: Map<Currency, Double>,
    val onClick: (userID: String) -> Unit,
)
