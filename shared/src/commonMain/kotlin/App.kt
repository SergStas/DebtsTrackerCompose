import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.models.Currency
import domain.models.Friend
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.theme.AppTheme
import ui.friendlist.FriendCard
import ui.friendlist.model.FriendItem

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterialApi::class)
@Composable
fun App() {
    val friends = listOf(
        FriendItem(
            friend = Friend(
                "id",
                "Guzel",
                null,
                false,
            ),
            debts = mapOf(
                Currency.Rub to 100.0,
                Currency.Usd to -35.0,
                Currency.Eur to -35.0,
                Currency.Kzt to 35.0,
                Currency.Gel to 0.0,
            ),
            onClick = {},
        ),
        FriendItem(
            friend = Friend(
                "id",
                "Danila",
                null,
                true,
            ),
            debts = mapOf(
                Currency.Rub to -100.0,
            ),
            onClick = {},
        ),
    )
    AppTheme.theme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            friends.forEach {
                FriendCard(it)
            }
        }
//        var greetingText by remember { mutableStateOf("Hello, World!") }
//        var showImage by remember { mutableStateOf(false) }
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = {
//                greetingText = "Hello, ${getPlatformName()}"
//                showImage = !showImage
//            }) {
//                Text(greetingText)
//            }
//            AnimatedVisibility(showImage) {
//                Image(
//                    painterResource("compose-multiplatform.xml"),
//                    null
//                )
//            }
//        }
    }
}

expect fun getPlatformName(): String