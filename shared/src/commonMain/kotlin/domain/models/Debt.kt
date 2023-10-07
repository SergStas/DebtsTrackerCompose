package domain.models

data class Debt(
    val lender: User,
    val borrower: User,
    val currency: Currency,
    val sum: Double,
    val creationDate: Long,
    val expirationDate: Long?,
    val description: String?,
    val status: Status,
) {
    fun secondUser(first: User) =
        if (first.userId == lender.userId) borrower else lender

    fun isIncoming(to: User) =
        to.userId == lender.userId

    enum class Status {
        ASSIGNED, DECLINED, ACCEPTED, PAID, CONFIRMED, CANCELLING, CANCELLED;
    }
}
