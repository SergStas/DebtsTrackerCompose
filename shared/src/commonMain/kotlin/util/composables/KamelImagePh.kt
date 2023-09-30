package util.composables

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@ExperimentalResourceApi
@Composable
fun KamelImagePh(
    contentResource: String,
    phResource: String,
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier = Modifier,
) =
    when (val resource = asyncPainterResource(contentResource)) {
        is Resource.Loading -> Image(painterResource(phResource), null)
        is Resource.Success -> Image(resource.value, null)
        is Resource.Failure -> Image(painterResource(phResource), null)
    }