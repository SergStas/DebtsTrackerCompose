package composables.screens.friendlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import data.network.avatarUrl
import domain.models.Currency
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import composables.screens.friendlist.model.FriendItem
import composables.theme.AppTheme
import util.composables.KamelImagePh

@OptIn(ExperimentalMaterialApi::class, ExperimentalResourceApi::class)
@Composable
fun FriendCard(item: FriendItem) =
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { item.onClick(item.user.userId) },
    ) {
        Row(Modifier.padding(AppTheme.Sizes.paddingNormal.dp)) {
            KamelImagePh(
                contentResource = item.user.avatarUrl,
                phResource = "ic_user_avatar_ph.xml",
            )
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = AppTheme.Sizes.paddingLarger.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = item.user.username,
                        style = AppTheme.fonts().h2,
                        modifier = Modifier.weight(.8f).wrapContentWidth(Alignment.Start),
                    )
                    Image(
                        imageVector =
                            if (item.user.isReal) Icons.Default.Check
                            else Icons.Default.Warning,
                        contentDescription = null,
                        modifier = Modifier.weight(.2f).wrapContentWidth(Alignment.End),
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                ) {
                    item.debts.forEach {
                        Image(
                            painter = painterResource(
                                when (it.key) {
                                    Currency.Rub -> "ic_rub.xml"
                                    Currency.Usd -> "ic_usd.xml"
                                    Currency.Eur -> "ic_eur.xml"
                                    Currency.Kzt -> "ic_kzt.xml"
                                    Currency.Gel -> "ic_gel.xml"
                                }
                            ),
                            colorFilter = ColorFilter.tint(
                                if (it.value >= 0) AppTheme.ColorCodes.c5
                                else AppTheme.ColorCodes.c6,
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(.4f / item.debts.size)
                                .wrapContentWidth(Alignment.Start),
                        )
                    }
                    if (!item.user.isReal) {
                        Text(
                            modifier = Modifier
                                .weight(.6f)
                                .wrapContentWidth(Alignment.End),
                            text = "User not confirmed",
                            style = AppTheme.fonts().h5,
                        )
                    }
                }
            }
        }
    }