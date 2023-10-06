package composables.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object AppTheme{
    @Composable
    fun theme(content: @Composable () -> Unit) =
        MaterialTheme(
            colors = colors(),
            shapes = shapes(),
            typography = fonts(),
            content = content,
        )

    @Composable
    fun shapes() = MaterialTheme.shapes.copy(
        small = RoundedCornerShape(0.dp),
        medium = RoundedCornerShape(16.dp),
        large = RoundedCornerShape(24.dp),
    )

    @Composable
    fun fonts() = MaterialTheme.typography.copy(
        h1 = MaterialTheme.typography.h1.copy(
            color = colors().onSecondary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        ),
        h2 = MaterialTheme.typography.h2.copy(
            color = colors().onSecondary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.Serif,
        ),
        h3 = MaterialTheme.typography.h3.copy(
            color = colors().onSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
        ),
        h4 = MaterialTheme.typography.h4.copy(
            color = colors().onSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        ),
        h5 = MaterialTheme.typography.h5.copy(
            color = colors().onSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        ),
        h6 = MaterialTheme.typography.h6.copy(
            color = colors().onSecondary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
        ),
        button = MaterialTheme.typography.button.copy(
            color = colors().onSecondary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
    )

    @Composable
    fun colors() = MaterialTheme.colors.copy(
        primary = ColorCodes.c1,
        primaryVariant = ColorCodes.c8,
        secondary = ColorCodes.c7,
        background = ColorCodes.c3,
        surface = ColorCodes.c4,
        onPrimary = ColorCodes.c3,
        onSecondary = ColorCodes.c2,
        onBackground = ColorCodes.c2,
        onSurface = ColorCodes.c2,
    )

    object ColorCodes {
        val c1 = Color(0xFF1A972E)
        val c2 = Color(0xFF000000)
        val c3 = Color(0xFFFFFFFF)
        val c4 = Color(0xFFD9D9D9)
        val c5 = Color(0xFF1CAF04)
        val c6 = Color(0xFF7E0000)
        val c7 = Color(0xFF858585)
        val c8 = Color(0xFF0C3009)
    }

    object Sizes {
        const val paddingLarge = 20
        const val paddingLarger = 16
        const val paddingNormal = 12
        const val paddingSmaller = 8
        const val paddingSmall = 4
        const val paddingExtraSmall = 2
    }
}