package composables.screens.splash

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import di.AppDiAware
import domain.repo.IAuthRepo
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance
import util.Constants

class SplashViewModel: AppDiAware, ViewModel() {
    private val authRepo by instance<IAuthRepo>()

    val state get() = _state.asStateFlow()
    private val _state = MutableStateFlow<State>(State.Loading)

    fun onAttach() {
        viewModelScope.launch {
            if (_state.value is State.Done) return@launch
            val started = getTimeMillis()
            val authed = authRepo.getAuthedUser() != null
            val elapsedTime = getTimeMillis() - started
            val remainingTime = Constants.SPLASH_DURATION_MS - elapsedTime
            if (remainingTime > 0) {
                delay(remainingTime)
            }
            _state.value = State.Done(authed)
        }
    }

    sealed interface State {
        data object Loading: State
        data class Done(val authorized: Boolean): State
    }
}