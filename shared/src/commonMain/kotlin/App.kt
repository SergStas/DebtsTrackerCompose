import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import composables.screens.tabmenu.TabMenuScreen
import composables.theme.AppTheme

@Composable
fun App() {
    AppTheme.theme {
        Navigator(TabMenuScreen())
    }
}

expect fun getPlatformName(): String