package composables.screens.tabmenu.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import composables.screens.tabmenu.friendlist.FriendListScreen
import res.StringResources

internal object FriendListTab: Tab {
    @Composable
    override fun Content() {
        FriendListScreen().Content()
    }

    override val options: TabOptions
        @Composable
        get() {
            val title = StringResources.instance.bottomNavTitleFriendList
            val icon = rememberVectorPainter(Icons.Default.Person)
            return remember {
                TabOptions(1u, title, icon)
            }
        }
}