package composables.screens.tabmenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import composables.navigation.tabs.FriendListTab
import composables.navigation.tabs.HomeTab
import composables.navigation.tabs.SettingsTab
import composables.theme.AppTheme

class TabMenuScreen: Screen {
    @Composable
    override fun Content() =
        Box(Modifier.fillMaxSize()) {
            TabNavigator(HomeTab) {
                Scaffold(
                    content = { CurrentTab() },
                    bottomBar = {
                        BottomNavigation(
                            backgroundColor = AppTheme.colors().surface,
                            contentColor = AppTheme.colors().primary,
                        ) {
                            TabNavigationItem(HomeTab)
                            TabNavigationItem(FriendListTab)
                            TabNavigationItem(SettingsTab)
                        }
                    }
                )
            }
        }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        BottomNavigationItem(
            selected = tabNavigator.current == tab,
            onClick = { tabNavigator.current = tab },
            icon = {
                Icon(
                    painter = tab.options.icon!!,
                    contentDescription = tab.options.title,
                    modifier = Modifier.scale(1.6f),
                )
            }
        )
    }
}