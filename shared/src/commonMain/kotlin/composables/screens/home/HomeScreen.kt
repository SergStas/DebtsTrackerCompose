package composables.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import composables.screens.create.CreateNewDebtScreen
import composables.screens.history.HistoryScreen
import composables.theme.AppTheme
import res.StringResources

class HomeScreen: Screen {
    @Composable
    override fun Content() {
        Column(Modifier.fillMaxSize().padding(AppTheme.Sizes.windowPadding.dp)) {
            InfoBar()
            Spacer(Modifier.height(AppTheme.Sizes.paddingNormal.dp))
            ButtonBar()
        }
    }

    @Composable
    private fun ButtonBar() {
        val navigator = LocalNavigator.current
        Button(
            onClick = { navigator?.push(CreateNewDebtScreen()) },
            modifier = Modifier.fillMaxWidth().padding(AppTheme.Sizes.paddingNormal.dp),
        ) {
            Text(
                text = StringResources.get().homeButtonNew,
                color = AppTheme.colors().onPrimary,
            )
        }
        Button(
            onClick = { navigator?.push(HistoryScreen()) },
            modifier = Modifier.fillMaxWidth().padding(AppTheme.Sizes.paddingNormal.dp)
        ) {
            Text(
                text = StringResources.get().homeButtonHistory,
                color = AppTheme.colors().onPrimary,
            )
        }
    }

    @Composable
    private fun InfoBar() {
        Card(Modifier.wrapContentHeight().fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(AppTheme.Sizes.paddingLarge.dp),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Text(
                    text = StringResources.get().homeTextDebtorsPh.replace("%s", "0"),
                    modifier = Modifier.align(alignment = Alignment.CenterVertically),
                    style = AppTheme.fonts().h2.copy(
                        color = AppTheme.ColorCodes.c5
                    ),
                )
                Text(
                    text = StringResources.get().homeTextDebtsPh.replace("%s", "0"),
                    modifier = Modifier.align(alignment = Alignment.CenterVertically),
                    style = AppTheme.fonts().h2.copy(
                        color = AppTheme.ColorCodes.c6
                    ),
                )
            }
        }
    }
}