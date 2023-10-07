package composables.navigation.graphs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import composables.screens.home.HomeScreen

class HomeNavScreen: Screen {
    @Composable
    override fun Content() {
        Navigator(HomeScreen())
    }
}