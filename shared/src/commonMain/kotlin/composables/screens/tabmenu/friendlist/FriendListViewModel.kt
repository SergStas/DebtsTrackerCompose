package composables.screens.tabmenu.friendlist

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import di.AppDiAware
import domain.models.Friend
import domain.usecases.friends.GetFriendsListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.kodein.di.instance

class FriendListViewModel: ViewModel(), AppDiAware {
    private val getFriendsListUseCase by instance<GetFriendsListUseCase>()

    val friends get() = _friends.asStateFlow()
    private val _friends = MutableStateFlow<List<Friend>>(emptyList())

    fun loadFriendList() {
        viewModelScope.launch {
            _friends.value = getFriendsListUseCase()
        }
    }
}