package composables.screens.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import composables.screens.history.model.HistoryViewModel
import util.static.getVm

class HistoryScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel = getVm(::HistoryViewModel)
        viewModel.loadContent()
        val debts by viewModel.debtList.collectAsState()
        Column(Modifier.fillMaxSize()) {
            debts.forEach {
                DebtHistoryCard(it)
            }
        }
    }
}