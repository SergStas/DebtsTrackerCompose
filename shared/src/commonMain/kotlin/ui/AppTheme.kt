package ui

import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.AppColors

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    val colors = MaterialTheme.colors.copy(
        primary = AppColors.c1,
        primaryVariant = AppColors.c8,
        secondary = AppColors.c7,
        background = AppColors.c3,
        surface = AppColors.c4,
        onPrimary = AppColors.c3,
        onSecondary = AppColors.c2,
        onBackground = AppColors.c2,
        onSurface = AppColors.c2,
    )
    val shapes = MaterialTheme.shapes.copy(
        small = AbsoluteCutCornerShape(0.dp),
        medium = AbsoluteCutCornerShape(12.dp),
        large = AbsoluteCutCornerShape(24.dp),
    )
    val typography = MaterialTheme.typography.copy(
        h1 = MaterialTheme.typography.h1.copy(
            color = colors.onPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        ),
        h2 = MaterialTheme.typography.h2.copy(
            color = colors.onPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
        ),
        h3 = MaterialTheme.typography.h3.copy(
            color = colors.onPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
        ),
        h4 = MaterialTheme.typography.h4.copy(
            color = colors.onPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        ),
        h5 = MaterialTheme.typography.h5.copy(
            color = colors.onPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        ),
        h6 = MaterialTheme.typography.h6.copy(
            color = colors.onPrimary,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
        ),
        button = MaterialTheme.typography.button.copy(
            color = colors.onPrimary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        ),
    )
    return MaterialTheme(
        colors = colors,
        shapes = shapes,
        typography = typography,
        content = content,
    )
}