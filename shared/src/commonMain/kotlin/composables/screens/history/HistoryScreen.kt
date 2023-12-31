package composables.screens.history

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import composables.theme.AppTheme
import util.static.getVm

class HistoryScreen: Screen {
    @Composable
    override fun Content() {
        val viewModel = getVm(::HistoryViewModel)
        viewModel.loadContent()
        val debts by viewModel.debtList.collectAsState()
        val loading = viewModel.loading.collectAsState()
        Box {
            Column(
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(AppTheme.Sizes.windowPadding.dp),
            ) {
                debts.forEach {
                    DebtHistoryCard(it)
                }
            }
            if (loading.value) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }
    }
}