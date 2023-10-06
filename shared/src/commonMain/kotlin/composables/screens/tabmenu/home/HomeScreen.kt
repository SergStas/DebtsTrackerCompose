package composables.screens.tabmenu.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import res.StringResources

class HomeScreen: Screen {
    @Composable
    override fun Content() {
        Box(Modifier.fillMaxSize()) {
            Text(StringResources.get().bottomNavTitleHome, modifier = Modifier.align(Alignment.Center))
        }
    }
}