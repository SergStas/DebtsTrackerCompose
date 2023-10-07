package composables.screens.history.model

import domain.models.Debt

data class DebtHistoryItem(
    private val origin: Debt,
    val sumDisplay: String,
    val avatarUrl: String?,
    val directionDisplay: String,
    val usernameDisplay: String,
    val creationDateDisplay: String,
    val expirationDateDisplay: String?,
    val statusDisplay: String,
    val description: String?,
    val overdueVisibility: Boolean,
    val warningVisibility: Boolean,
    val isIncoming: Boolean,
    val onClick: (DebtHistoryItem) -> Unit,
) {
    fun toDomainModel() = origin
}
