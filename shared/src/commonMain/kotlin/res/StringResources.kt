package res

abstract class StringResources {
    companion object {
        fun get() = EngStringResources
    }

    abstract val bottomNavTitleHome: String
    abstract val bottomNavTitleFriendList: String
    abstract val bottomNavTitleSettings: String

    abstract val friendCardTextIsFake: String

    abstract val homeTextDebtsPh: String
    abstract val homeTextDebtorsPh: String
    abstract val homeButtonHistory: String
    abstract val homeButtonNew: String
}