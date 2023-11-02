package composables.navigation.graphs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import composables.screens.splash.SplashScreen

class RootNavScreen: Screen {
    @Composable
    override fun Content() {
        Navigator(SplashScreen())
    }
}