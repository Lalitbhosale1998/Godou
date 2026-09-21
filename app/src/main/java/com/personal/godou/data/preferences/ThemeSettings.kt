package com.personal.godou.data.preferences

enum class AppLanguage {
    ENGLISH, JAPANESE
}

enum class ColorIntensityPreset {
    NEUTRAL, SOFT, BRIGHT, BOLD;

    companion object {
        fun fromStorage(value: String?): ColorIntensityPreset {
            return try {
                value?.let { valueOf(it) } ?: SOFT
            } catch (e: Exception) {
                SOFT
            }
        }
    }
}

data class ThemeSettings(
    val darkThemePreference: DarkThemePreference = DarkThemePreference.SYSTEM,
    val useDynamicColor: Boolean = true,
    val appLanguage: AppLanguage = AppLanguage.JAPANESE,
    val topAppBarBackground: TopAppBarBackground = TopAppBarBackground.PRIMARY_CONTAINER,
    val themeStyle: ThemeStyle = ThemeStyle.DEFAULT,
    val appFont: AppFont = AppFont.NUNITO,
    val backdropPattern: BackdropPattern = BackdropPattern.NONE,
    val glowIntensity: GlowIntensity = GlowIntensity.SUBTLE,
    val touchSynesthesia: TouchSynesthesia = TouchSynesthesia.SUBTLE,
    val dynamicTonalStyle: DynamicTonalStyle = DynamicTonalStyle.TONAL_SPOT,
    val intensityPreset: ColorIntensityPreset = ColorIntensityPreset.SOFT,
    val themeFlavor: ThemeFlavor = ThemeFlavor.DYNAMIC_MATERIAL,
    val dynamicColorChromaScale: Float = 1.0f,
    val privacyModeEnabled: Boolean = false,
    val isSetupComplete: Boolean = true
)
