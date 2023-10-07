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

    abstract val debtSumPh: String
    abstract val debtOverdueMsg: String
    abstract val debtLabelDescription: String
    abstract val debtLabelNoDescription: String
    abstract val debtLabelIncoming: String
    abstract val debtLabelOutgoing: String

    abstract val authTitleRegister: String
    abstract val authTitleLogin: String
    abstract val authLabelUsername: String
    abstract val authLabelPassword: String
    abstract val authLabelPasswordRepeat: String
    abstract val authTextRedirectToLoginPrefix: String
    abstract val authTextRedirectToRegisterPrefix: String
    abstract val authTextRedirectToLoginPostfix: String
    abstract val authTextRedirectToRegisterPostfix: String
    abstract val authButtonLogin: String
    abstract val authButtonRegister: String
}