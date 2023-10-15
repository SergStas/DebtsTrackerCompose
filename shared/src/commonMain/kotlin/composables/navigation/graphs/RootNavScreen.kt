package composables.navigation.graphs

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import composables.screens.auth.AuthScreen
import composables.screens.create.CreateNewDebtScreen

class RootNavScreen: Screen {
    @Composable
    override fun Content() {
        Navigator(CreateNewDebtScreen())
    }
}