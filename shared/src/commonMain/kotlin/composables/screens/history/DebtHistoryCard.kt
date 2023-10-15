@file:OptIn(ExperimentalResourceApi::class)

package composables.screens.history

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import composables.screens.history.model.DebtHistoryItem
import composables.theme.AppTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import res.StringResources
import util.composables.KamelImagePh

@Composable
fun DebtHistoryCard(item: DebtHistoryItem) =
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.Sizes.paddingSmaller.dp),
    ) {
        Column {
            Heading(item)
            DescriptionBlock(item)
        }
    }

@Composable
private fun DescriptionBlock(item: DebtHistoryItem) {
    Column(Modifier.padding(horizontal = AppTheme.Sizes.paddingLarge.dp)) {
        Text(
            text = StringResources.get().run {
                if (item.description != null) debtLabelDescription else debtLabelNoDescription
            },
            style = AppTheme.fonts().h2,
            modifier = Modifier.padding(
                bottom = AppTheme.Sizes.run {
                    if (item.description == null) paddingLarge else paddingSmall
                }.dp,
            )
        )
        if (item.description != null) {
            Text(
                text = item.description,
                style = AppTheme.fonts().h5,
                maxLines = 6,
                modifier = Modifier.padding(bottom = AppTheme.Sizes.paddingLarge.dp),
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun Heading(item: DebtHistoryItem) =
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(
                vertical = AppTheme.Sizes.paddingNormal.dp,
                horizontal = AppTheme.Sizes.paddingLarge.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        HeadingUserInfo(item)
        HeadingDebtDetails(item)
    }

@Composable
private fun HeadingDebtDetails(item: DebtHistoryItem) =
    Column(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
        horizontalAlignment = Alignment.End,
    ) {
        Text(
            text = item.statusDisplay,
            style = AppTheme.fonts().h2,
            textAlign = TextAlign.End,
        )
        Text(
            text = item.creationDateDisplay,
            style = AppTheme.fonts().h5,
            modifier = Modifier.padding(top = AppTheme.Sizes.paddingSmall.dp),
        )
        if (item.expirationDateDisplay != null) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.padding(top = AppTheme.Sizes.paddingSmall.dp),
            ) {
                Image(
                    painter = rememberVectorPainter(Icons.Default.Warning),
                    contentDescription = null,
                    modifier = Modifier
                        .height(AppTheme.Sizes.paddingLarge.dp)
                        .padding(end = AppTheme.Sizes.paddingSmaller.dp),
                    alignment = Alignment.Center,
                )
                Text(item.expirationDateDisplay, style = AppTheme.fonts().h5)
            }
        }
        if (item.overdueVisibility) {
            Text(
                text = StringResources.get().debtOverdueMsg,
                modifier = Modifier.padding(top = AppTheme.Sizes.paddingSmall.dp),
                style = AppTheme.fonts().h5.copy(
                    color = AppTheme.ColorCodes.c6,
                )
            )
        }
    }

@Composable
private fun HeadingUserInfo(item: DebtHistoryItem) =
    Column(Modifier.fillMaxWidth(.65f)) {
        Text(
            text = item.sumDisplay,
            style = AppTheme.fonts().h1.copy(fontSize = 32.sp),
            color = if (item.isIncoming) AppTheme.ColorCodes.c5 else AppTheme.ColorCodes.c6,
            modifier = Modifier.padding(bottom = AppTheme.Sizes.paddingLarger.dp)
        )
        Row(Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
            KamelImagePh(
                contentResource = item.avatarUrl ?: "",
                phResource = "ic_user_avatar_ph.xml",
                modifier = Modifier.fillMaxWidth(.5f),
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(start = AppTheme.Sizes.paddingLarger.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(item.directionDisplay, style = AppTheme.fonts().h5)
                Text(item.usernameDisplay, style = AppTheme.fonts().h4)
            }
        }
    }
