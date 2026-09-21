package com.personal.godou.ui.theme

import android.content.Context
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import com.personal.godou.data.preferences.ThemeFlavor

/**
 * Modern Android 17 Dynamic Monet Color Palette Provider.
 * Enforces pure dynamic wallpaper color extraction with zero static hex color overrides.
 */
object ThemePalettes {

    fun getColorScheme(flavor: ThemeFlavor, isDark: Boolean, context: Context): ColorScheme {
        return if (isDark) {
            dynamicDarkColorScheme(context)
        } else {
            dynamicLightColorScheme(context)
        }
    }
}
