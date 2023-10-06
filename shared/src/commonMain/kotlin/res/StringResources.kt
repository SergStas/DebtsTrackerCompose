package res

abstract class StringResources {
    companion object {
        fun get() = EngStringResources
    }

    abstract val bottomNavTitleHome: String
    abstract val bottomNavTitleFriendList: String
    abstract val bottomNavTitleSettings: String

    abstract val friendCardTIsFake: String
}