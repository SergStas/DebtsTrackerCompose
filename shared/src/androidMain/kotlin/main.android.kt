import android.content.Context
import androidx.compose.runtime.Composable
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.Settings

lateinit var appContext: Context

actual fun getPlatformName(): String = "Android"

actual fun getSettings(): Settings {
    val prefs = appContext.getSharedPreferences("app", Context.MODE_PRIVATE)
    return AndroidSettings(prefs)
}

@Composable fun MainView() = App()
