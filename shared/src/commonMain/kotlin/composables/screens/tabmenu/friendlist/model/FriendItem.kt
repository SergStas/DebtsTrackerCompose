package composables.screens.tabmenu.friendlist.model

import domain.models.Currency
import domain.models.Friend

data class FriendItem(
    val friend: Friend,
    val debts: Map<Currency, Double>,
    val onClick: (userID: String) -> Unit,
)
