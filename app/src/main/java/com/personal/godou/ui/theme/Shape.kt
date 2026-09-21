package com.personal.godou.ui.theme

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/**
 * Material 3 Expressive Shape scale for Godou.
 * Employs asymmetric continuous curves and exaggerated corner radii for organic surfaces.
 */
val GodouShapes = Shapes(
    extraSmall = RoundedCornerShape(topStart = CornerSize(12.dp), bottomEnd = CornerSize(12.dp), topEnd = CornerSize(4.dp), bottomStart = CornerSize(4.dp)),
    small = RoundedCornerShape(topStart = CornerSize(20.dp), bottomEnd = CornerSize(20.dp), topEnd = CornerSize(8.dp), bottomStart = CornerSize(8.dp)),
    medium = RoundedCornerShape(topStart = CornerSize(32.dp), bottomEnd = CornerSize(32.dp), topEnd = CornerSize(14.dp), bottomStart = CornerSize(14.dp)),
    large = RoundedCornerShape(topStart = CornerSize(44.dp), bottomEnd = CornerSize(44.dp), topEnd = CornerSize(20.dp), bottomStart = CornerSize(20.dp)),
    extraLarge = RoundedCornerShape(topStart = CornerSize(52.dp), topEnd = CornerSize(52.dp), bottomStart = CornerSize(24.dp), bottomEnd = CornerSize(24.dp))
)
