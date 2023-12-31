import androidx.compose.runtime.Composable
import com.russhwolf.settings.Settings
import composables.navigation.graphs.RootNavScreen
import composables.theme.AppTheme

@Composable
fun App() {
    AppTheme.theme {
        RootNavScreen().Content()
    }
}

expect fun getPlatformName(): String

expect fun getSettings(): Settings