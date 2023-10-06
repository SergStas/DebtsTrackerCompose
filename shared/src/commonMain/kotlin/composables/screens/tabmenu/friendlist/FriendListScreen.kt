package composables.screens.tabmenu.friendlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import composables.screens.tabmenu.friendlist.model.FriendItem
import composables.theme.AppTheme
import util.static.getVm

class FriendListScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel = getVm { FriendListViewModel() }
        viewModel.loadFriendList()
        val friendList by viewModel.friends.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppTheme.Sizes.windowPadding.dp),
            ) {
            friendList
                .map { FriendItem(it, emptyMap()) {} }
                .forEach {
                    FriendCard(it)
                    Spacer(Modifier.height(AppTheme.Sizes.paddingNormal.dp))
                }
        }
    }
}