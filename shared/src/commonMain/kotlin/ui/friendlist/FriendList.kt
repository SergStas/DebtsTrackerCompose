package ui.friendlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.friendlist.model.FriendItem

@ExperimentalMaterialApi
@ExperimentalResourceApi
@Composable
fun FriendList(viewModel: FriendListViewModel) {
    viewModel.loadFriendList()
    val friendList by viewModel.friends.collectAsState()
    Column(Modifier.fillMaxSize()) {
        friendList
            .map { FriendItem(it, emptyMap()) {} }
            .forEach { FriendCard(it) }
    }
}