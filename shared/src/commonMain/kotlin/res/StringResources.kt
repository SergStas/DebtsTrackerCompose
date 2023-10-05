package res

abstract class StringResources {
    companion object {
        val instance get() = EngStringResources
    }

    abstract val bottomNavTitleHome: String
    abstract val bottomNavTitleFriendList: String
    abstract val bottomNavTitleSettings: String

    abstract val friendCardTIsFake: String
}