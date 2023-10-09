package composables.screens.auth

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import di.AppDiAware
import domain.repo.IAuthRepo
import domain.usecases.auth.GetAuthedUserUseCase
import domain.usecases.auth.LoginUseCase
import domain.usecases.auth.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance
import util.Constants

class AuthViewModel: AppDiAware, ViewModel() {
    private val getAuthedUser by instance<GetAuthedUserUseCase>()
    private val login by instance<LoginUseCase>()
    private val register by instance<RegisterUseCase>()

    val authed: StateFlow<Boolean> get() = _authed
    private val _authed = MutableStateFlow(false)

    val validationResult: StateFlow<Validation> get() = _validationResult
    private val _validationResult = MutableStateFlow(Validation.None)

    val mode: StateFlow<Mode> get() = _mode
    private val _mode = MutableStateFlow(Mode.Registration)

    fun checkAuth() {
        viewModelScope.launch {
            _authed.emit(getAuthedUser() != null)
        }
    }

    fun switchMode() {
        viewModelScope.launch {
            _mode.run { emit(value.switch()) }
        }
    }

    fun submit(
        username: String?,
        password: String?,
        passwordRepeat: String? = null,
    ) {
        viewModelScope.launch {
            val validationResult = when {
                username.run { isNullOrBlank() || isEmpty() } -> Validation.UsernameIsEmpty
                password.run { isNullOrBlank() || isEmpty() } -> Validation.PasswordIsEmpty
                username!!.length < Constants.USERNAME_MIN_LENGTH -> Validation.UsernameTooShort
                password!!.length < Constants.PASSWORD_MIN_LENGTH -> Validation.PasswordTooShort
                _mode.value == Mode.Registration && password != passwordRepeat -> Validation.PasswordsNotMatch
                else -> Validation.Success
            }
            if (validationResult != Validation.Success) {
                _validationResult.emit(validationResult)
            } else {
                val result = when (mode.value) {
                    Mode.Login -> login(username!!, password!!)
                    Mode.Registration -> register(username!!, password!!)
                }
                val operationResult = when(result) {
                    IAuthRepo.AuthResult.Login.Invalid -> Validation.InvalidCredentials
                    is IAuthRepo.AuthResult.Success -> Validation.Success
                    IAuthRepo.AuthResult.Register.PasswordIsInvalid -> Validation.PasswordTooShort
                    IAuthRepo.AuthResult.Register.UsernameIsInvalid -> Validation.UsernameTooShort
                    IAuthRepo.AuthResult.Register.UsernameIsTaken -> Validation.UsernameOccupied
                    IAuthRepo.AuthResult.UnknownError -> Validation.UnknownError
                }
                _validationResult.emit(operationResult)
                checkAuth()
            }
        }
    }

    enum class Mode {
        Login, Registration;

        fun switch() = when (this) {
            Login -> Registration
            Registration -> Login
        }
    }

    enum class Validation {
        None,
        Success,
        UsernameIsEmpty,
        PasswordIsEmpty,
        UsernameTooShort,
        UsernameOccupied,
        PasswordTooShort,
        InvalidCredentials,
        PasswordsNotMatch,
        UnknownError;

        val isError get() = this !in listOf(None, Success)
    }
}