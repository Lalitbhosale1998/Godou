package com.personal.godou.ui.theme

import android.content.Context
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.personal.godou.data.preferences.AppFont
import com.personal.godou.data.preferences.BackdropPattern
import com.personal.godou.data.preferences.GlowIntensity
import com.personal.godou.data.preferences.ThemeSettings
import com.personal.godou.data.preferences.TouchSynesthesia
import com.personal.godou.data.preferences.StudyPreferences

object ExpressivePhysics {
    fun <T> fluidSnappy() = spring<T>(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessLow)
    fun <T> fluidBouncy() = spring<T>(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
    fun <T> heroMorphSpec() = spring<T>(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessVeryLow)
    fun <T> sheetEnterSpec() = spring<T>(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
    fun <T> sheetExitSpec() = spring<T>(dampingRatio = Spring.DampingRatioNoBouncy, stiffness = Spring.StiffnessMedium)
}

val LocalTouchSynesthesia = staticCompositionLocalOf { TouchSynesthesia.SUBTLE }
val LocalGlowIntensity = staticCompositionLocalOf { GlowIntensity.SUBTLE }
val LocalThemeSettings = staticCompositionLocalOf { ThemeSettings() }

private fun getTypography(appFont: AppFont): Typography {
    val displayFontFamily = when (appFont) {
        AppFont.NUNITO -> NunitoFontFamily
        AppFont.NOTO_SANS_JP -> NotoSansJPFontFamily
        AppFont.M_PLUS_1 -> MPlus1FontFamily
        AppFont.DELA_GOTHIC_ONE -> DelaGothicOneFontFamily
        AppFont.HACHI_MARU_POP -> HachiMaruPopFontFamily
        AppFont.KAISEI_DECOL -> KaiseiDecolFontFamily
        AppFont.KAISEI_OPTI -> KaiseiOptiFontFamily
        AppFont.KOSUGI_MARU -> KosugiMaruFontFamily
        AppFont.MOCHIY_POP_P_ONE -> MochiyPopPOneFontFamily
        AppFont.POTTA_ONE -> PottaOneFontFamily
        AppFont.RAMPART_ONE -> RampartOneFontFamily
        AppFont.REGGAE_ONE -> ReggaeOneFontFamily
        AppFont.ROCKNROLL_ONE -> RocknRollOneFontFamily
        AppFont.WDXL_LUBRIFONT_JPN -> WDXLLubrifontJPNFontFamily
        AppFont.YUJI_MAI -> YujiMaiFontFamily
        AppFont.YUSEI_MAGIC -> YuseiMagicFontFamily
        AppFont.GOOGLE_SANS_FLEX -> GoogleSansFlexFontFamily
        AppFont.OUTFIT -> OutfitFontFamily
        AppFont.PLAYFAIR -> PlayfairFontFamily
        AppFont.MONOSPACE -> FontFamily.Monospace
        AppFont.SYSTEM_SANS -> FontFamily.SansSerif
    }

    val bodyFontFamily = displayFontFamily

    val isJapaneseFont = when (appFont) {
        AppFont.NUNITO, AppFont.MONOSPACE, AppFont.SYSTEM_SANS,
        AppFont.OUTFIT, AppFont.PLAYFAIR, AppFont.GOOGLE_SANS_FLEX -> false
        else -> true
    }

    val wideTransform = if (isJapaneseFont) {
        androidx.compose.ui.text.style.TextGeometricTransform(scaleX = 1.0f)
    } else {
        ExpTitleTransform
    }
    return Typography(
        displayLarge = Typography.displayLarge.copy(fontFamily = displayFontFamily, textGeometricTransform = wideTransform),
        displayMedium = Typography.displayMedium.copy(fontFamily = displayFontFamily, textGeometricTransform = wideTransform),
        displaySmall = Typography.displaySmall.copy(fontFamily = displayFontFamily, textGeometricTransform = wideTransform),
        headlineLarge = Typography.headlineLarge.copy(fontFamily = displayFontFamily, textGeometricTransform = wideTransform),
        headlineMedium = Typography.headlineMedium.copy(fontFamily = displayFontFamily),
        headlineSmall = Typography.headlineSmall.copy(fontFamily = displayFontFamily),
        titleLarge = Typography.titleLarge.copy(fontFamily = bodyFontFamily),
        titleMedium = Typography.titleMedium.copy(fontFamily = bodyFontFamily),
        titleSmall = Typography.titleSmall.copy(fontFamily = bodyFontFamily),
        bodyLarge = Typography.bodyLarge.copy(fontFamily = bodyFontFamily),
        bodyMedium = Typography.bodyMedium.copy(fontFamily = bodyFontFamily),
        bodySmall = Typography.bodySmall.copy(fontFamily = bodyFontFamily),
        labelLarge = Typography.labelLarge.copy(fontFamily = bodyFontFamily),
        labelMedium = Typography.labelMedium.copy(fontFamily = bodyFontFamily),
        labelSmall = Typography.labelSmall.copy(fontFamily = bodyFontFamily),
    )
}

fun Modifier.glow(
    color: Color,
    radius: androidx.compose.ui.unit.Dp = 8.dp,
    intensity: GlowIntensity,
    shape: Shape = RoundedCornerShape(12.dp)
): Modifier = composed {
    if (intensity == GlowIntensity.OFF) return@composed this
    
    val alphaFactor = when (intensity) {
        GlowIntensity.PULSING -> 0.55f
        GlowIntensity.SUBTLE -> 0.25f
        GlowIntensity.NEON -> 0.75f
        else -> 0.0f
    }

    this.drawBehind {
        val shadowRadius = radius.toPx()
        val paint = Paint().asFrameworkPaint().apply {
            this.color = android.graphics.Color.TRANSPARENT
            setShadowLayer(
                shadowRadius,
                0f,
                0f,
                color.copy(alpha = alphaFactor).toArgb()
            )
        }
        
        drawIntoCanvas { canvas ->
            val outline = shape.createOutline(size, layoutDirection, this)
            when (outline) {
                is Outline.Rectangle -> {
                    canvas.nativeCanvas.drawRect(
                        0f, 0f, size.width, size.height, paint
                    )
                }
                is Outline.Rounded -> {
                    val rect = outline.roundRect
                    canvas.nativeCanvas.drawRoundRect(
                        rect.left, rect.top, rect.right, rect.bottom,
                        rect.topLeftCornerRadius.x, rect.topLeftCornerRadius.y,
                        paint
                    )
                }
                is Outline.Generic -> {
                    canvas.nativeCanvas.drawPath(
                        outline.path.asAndroidPath(), paint
                    )
                }
            }
        }
    }
}

fun Modifier.expressiveBackground(
    isDark: Boolean = false,
    isPrimaryContainer: Boolean = false,
    primaryColor: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified,
    pattern: BackdropPattern = BackdropPattern.NONE
): Modifier = this.composed {
    val backgroundColor = MaterialTheme.colorScheme.background

    this.drawBehind {
        drawRect(color = if (containerColor != Color.Unspecified) containerColor else backgroundColor)
    }
}

@Composable
fun GodouTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeSettings: ThemeSettings = LocalThemeSettings.current,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = ThemePalettes.getColorScheme(themeSettings.themeFlavor, darkTheme, context)
    val shapes = GodouShapes
    val typography = getTypography(themeSettings.appFont)

    CompositionLocalProvider(
        LocalTouchSynesthesia provides themeSettings.touchSynesthesia,
        LocalGlowIntensity provides themeSettings.glowIntensity,
        LocalThemeSettings provides themeSettings
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

val LocalStudyPreferences = staticCompositionLocalOf { StudyPreferences() }

@Composable
fun ThemeSettingsProvider(
    themeSettings: ThemeSettings,
    content: @Composable () -> Unit
) {
    GodouTheme(
        darkTheme = themeSettings.darkThemePreference.isDark(isSystemInDarkTheme()),
        themeSettings = themeSettings,
        content = content
    )
}

@Composable
fun StudySettingsProvider(
    studyPreferences: StudyPreferences,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalStudyPreferences provides studyPreferences,
        content = content
    )
}
