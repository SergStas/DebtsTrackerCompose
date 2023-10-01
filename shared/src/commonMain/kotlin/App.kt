import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.friendlist.FriendList
import ui.friendlist.FriendListViewModel
import ui.theme.AppTheme
import util.static.getVm

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
@Composable
fun App() {
    AppTheme.theme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            val friendsViewModel = getVm(::FriendListViewModel)
            FriendList(friendsViewModel)
        }
    }
}

expect fun getPlatformName(): String