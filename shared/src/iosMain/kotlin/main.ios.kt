import androidx.compose.ui.window.ComposeUIViewController
import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

actual fun getPlatformName(): String = "iOS"

actual fun getSettings(): Settings {
    val defaults = NSUserDefaults.standardUserDefaults
    return AppleSettings(defaults)
}

fun MainViewController() = ComposeUIViewController { App() }