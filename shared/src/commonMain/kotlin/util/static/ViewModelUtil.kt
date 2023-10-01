package util.static

import androidx.compose.runtime.Composable
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import dev.icerock.moko.mvvm.viewmodel.ViewModel

@Composable
inline fun<reified T: ViewModel> getVm(crossinline f: () -> T) =
    getViewModel(Unit, viewModelFactory(f))