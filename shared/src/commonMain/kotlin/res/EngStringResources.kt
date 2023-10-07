package res

object EngStringResources: StringResources() {
    override val bottomNavTitleHome = "Home"
    override val bottomNavTitleFriendList = "FriendList"
    override val bottomNavTitleSettings = "Settings"

    override val friendCardTextIsFake = "User not confirmed"

    override val homeTextDebtsPh = "%s debts"
    override val homeTextDebtorsPh = "%s debtors"
    override val homeButtonHistory = "All entries"
    override val homeButtonNew = "New entry"

    override val debtSumPh = "%s %s"
    override val debtOverdueMsg = "Overdue!"
    override val debtLabelDescription = "Description"
    override val debtLabelNoDescription = "No description"

    override val debtLabelIncoming = "to receive from"
    override val debtLabelOutgoing = "to pay to"
}