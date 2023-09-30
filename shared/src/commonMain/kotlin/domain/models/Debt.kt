package domain.models

data class Debt(
    val lender: Friend,
    val borrower: Friend,
    val currency: Currency,
    val sum: Double,
    val creationDate: Long,
    val expirationDate: Long?,
    val description: String?,
    val status: Status,
) {
    enum class Status {
        ASSIGNED, DECLINED, ACCEPTED, PAID, CONFIRMED, CANCELLING, CANCELLED;
    }
}
