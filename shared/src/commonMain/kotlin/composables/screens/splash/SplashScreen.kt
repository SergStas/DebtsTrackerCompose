package composables.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import composables.screens.auth.AuthScreen
import composables.screens.tabmenu.TabMenuScreen
import composables.theme.AppTheme
import util.static.getVm

class SplashScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel = getVm(::SplashViewModel)
        val state = viewModel.state.collectAsState()
        viewModel.onAttach()
        when (val value = state.value) {
            SplashViewModel.State.Loading -> Splash()
            is SplashViewModel.State.Done ->
                if (value.authorized) TabMenuScreen().Content()
                else AuthScreen().Content()
        }
    }

    @Composable
    private fun Splash() {
        Box(Modifier.fillMaxSize()) {
            Text(
                text = "Debts Tracker",
                style = AppTheme.fonts().h1,
                color = AppTheme.colors().primary,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}