package composables.screens.tabmenu.friendlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import composables.screens.tabmenu.friendlist.model.FriendItem
import util.static.getVm

class FriendListScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel = getVm { FriendListViewModel() }
        viewModel.loadFriendList()
        val friendList by viewModel.friends.collectAsState()
        Column(Modifier.fillMaxSize()) {
            friendList
                .map { FriendItem(it, emptyMap()) {} }
                .forEach { FriendCard(it) }
        }
    }
}