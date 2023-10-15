package res

import util.Constants

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

    override val authTitleRegister = "Sign up"
    override val authTitleLogin = "Sign in"
    override val authLabelUsername = "Username"
    override val authLabelPassword = "Password"
    override val authLabelPasswordRepeat = "Repeat password"
    override val authTextRedirectToLoginPrefix = "Already have an account? "
    override val authTextRedirectToRegisterPrefix = "Don't have an account? "
    override val authTextRedirectToLoginPostfix = "Sign in"
    override val authTextRedirectToRegisterPostfix = "Sign up"
    override val authButtonLogin = "Sign in"
    override val authButtonRegister = "Sign up"
    override val authErrorUsernameEmpty = "Username unspecified"
    override val authErrorUsernameTooShort = "Username length must be at least ${Constants.USERNAME_MIN_LENGTH} symbols"
    override val authErrorPasswordEmpty = "Password unspecified"
    override val authErrorPasswordTooShort = "Password length must be at least ${Constants.PASSWORD_MIN_LENGTH} symbols"
    override val authErrorUsernameOccupied = "Username is taken"
    override val authErrorPasswordsNotMatch = "Passwords not match"
    override val authErrorInvalidCredentials = "Invalid credentials"
    override val authErrorUnknownError = "Invalid error has occurred"

    override val newDebtClientLabel = "Client:"
    override val newDebtClientDefaultValue = "Select a client..."
    override val newDebtSumLabel = "Sum:"
    override val newDebtDirectionLabelIncoming = "Receive debt from..."
    override val newDebtDirectionLabelOutgoing = "Pay debt to..."
    override val newDebtLabelDesc = "Description"
    override val newDebtDescPh = "Type something..."
    override val newDebtDatePh = "dd-mm-yyyy"
    override val newDebtLabelDate = "Expiration date"
    override val newDebtButtonDone = "Create"
}