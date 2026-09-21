package com.personal.godou.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private object Keys {
    val DARK_THEME = stringPreferencesKey("dark_theme")
    val DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")
    val APP_LANGUAGE = stringPreferencesKey("app_language")
    val PRIVACY_MODE = booleanPreferencesKey("privacy_mode")
    val TOP_APP_BAR_BACKGROUND = stringPreferencesKey("top_app_bar_background")
    val THEME_STYLE = stringPreferencesKey("theme_style")
    val APP_FONT = stringPreferencesKey("app_font")
    val BACKDROP_PATTERN = stringPreferencesKey("backdrop_pattern")
    val GLOW_INTENSITY = stringPreferencesKey("glow_intensity")
    val TOUCH_SYNESTHESIA = stringPreferencesKey("touch_synesthesia")
    val DYNAMIC_TONAL_STYLE = stringPreferencesKey("dynamic_tonal_style")
    val INTENSITY_PRESET = stringPreferencesKey("intensity_preset")
    val THEME_FLAVOR = stringPreferencesKey("theme_flavor")
    val DYNAMIC_COLOR_CHROMA_SCALE = floatPreferencesKey("dynamic_color_chroma_scale")
    val IS_SETUP_COMPLETE = booleanPreferencesKey("is_setup_complete")

    // Study & Typography Preference Keys
    val FURIGANA_MODE = stringPreferencesKey("furigana_mode")
    val SHOW_ROMAJI = booleanPreferencesKey("show_romaji")
    val DAILY_GOAL_WORDS = intPreferencesKey("daily_goal_words")
    val SRS_ALGORITHM = stringPreferencesKey("srs_algorithm")
    val DECK_ORDER = stringPreferencesKey("deck_order")
    val DAILY_REMINDER_ENABLED = booleanPreferencesKey("daily_reminder_enabled")
    val DAILY_REMINDER_TIME = stringPreferencesKey("daily_reminder_time")
}

@Singleton
class UserPreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val themeSettings: Flow<ThemeSettings> = dataStore.data.map { prefs ->
        ThemeSettings(
            darkThemePreference = DarkThemePreference.fromStorage(prefs[Keys.DARK_THEME]),
            useDynamicColor = prefs[Keys.DYNAMIC_COLOR] ?: true,
            appLanguage = AppLanguage.valueOf(prefs[Keys.APP_LANGUAGE] ?: AppLanguage.JAPANESE.name),
            privacyModeEnabled = prefs[Keys.PRIVACY_MODE] ?: false,
            topAppBarBackground = TopAppBarBackground.valueOf(
                prefs[Keys.TOP_APP_BAR_BACKGROUND] ?: TopAppBarBackground.PRIMARY_CONTAINER.name
            ),
            themeStyle = ThemeStyle.fromStorage(prefs[Keys.THEME_STYLE]),
            appFont = AppFont.fromStorage(prefs[Keys.APP_FONT]),
            backdropPattern = BackdropPattern.fromStorage(prefs[Keys.BACKDROP_PATTERN]),
            glowIntensity = GlowIntensity.fromStorage(prefs[Keys.GLOW_INTENSITY]),
            touchSynesthesia = TouchSynesthesia.fromStorage(prefs[Keys.TOUCH_SYNESTHESIA]),
            dynamicTonalStyle = DynamicTonalStyle.fromStorage(prefs[Keys.DYNAMIC_TONAL_STYLE]),
            intensityPreset = ColorIntensityPreset.fromStorage(prefs[Keys.INTENSITY_PRESET]),
            themeFlavor = ThemeFlavor.fromStorage(prefs[Keys.THEME_FLAVOR]),
            dynamicColorChromaScale = prefs[Keys.DYNAMIC_COLOR_CHROMA_SCALE] ?: 1.0f,
            isSetupComplete = prefs[Keys.IS_SETUP_COMPLETE] ?: true
        )
    }

    val studyPreferences: Flow<StudyPreferences> = dataStore.data.map { prefs ->
        StudyPreferences(
            furiganaMode = FuriganaMode.fromStorage(prefs[Keys.FURIGANA_MODE]),
            showRomaji = prefs[Keys.SHOW_ROMAJI] ?: false,
            dailyGoalWords = prefs[Keys.DAILY_GOAL_WORDS] ?: 4,
            srsAlgorithm = SrsAlgorithm.fromStorage(prefs[Keys.SRS_ALGORITHM]),
            deckOrder = DeckOrder.fromStorage(prefs[Keys.DECK_ORDER]),
            dailyReminderEnabled = prefs[Keys.DAILY_REMINDER_ENABLED] ?: true,
            dailyReminderTime = prefs[Keys.DAILY_REMINDER_TIME] ?: "20:00"
        )
    }

    suspend fun setDarkThemePreference(value: DarkThemePreference) {
        dataStore.edit { it[Keys.DARK_THEME] = value.name }
    }

    suspend fun setUseDynamicColor(enabled: Boolean) {
        dataStore.edit { it[Keys.DYNAMIC_COLOR] = enabled }
    }

    suspend fun setAppLanguage(language: AppLanguage) {
        dataStore.edit { it[Keys.APP_LANGUAGE] = language.name }
    }

    suspend fun setPrivacyModeEnabled(enabled: Boolean) {
        dataStore.edit { it[Keys.PRIVACY_MODE] = enabled }
    }

    suspend fun setTopAppBarBackground(background: TopAppBarBackground) {
        dataStore.edit { it[Keys.TOP_APP_BAR_BACKGROUND] = background.name }
    }

    suspend fun setThemeStyle(style: ThemeStyle) {
        dataStore.edit { it[Keys.THEME_STYLE] = style.name }
    }

    suspend fun setAppFont(font: AppFont) {
        dataStore.edit { it[Keys.APP_FONT] = font.name }
    }

    suspend fun setBackdropPattern(pattern: BackdropPattern) {
        dataStore.edit { it[Keys.BACKDROP_PATTERN] = pattern.name }
    }

    suspend fun setGlowIntensity(intensity: GlowIntensity) {
        dataStore.edit { it[Keys.GLOW_INTENSITY] = intensity.name }
    }

    suspend fun setTouchSynesthesia(synesthesia: TouchSynesthesia) {
        dataStore.edit { it[Keys.TOUCH_SYNESTHESIA] = synesthesia.name }
    }

    suspend fun setDynamicTonalStyle(style: DynamicTonalStyle) {
        dataStore.edit { it[Keys.DYNAMIC_TONAL_STYLE] = style.name }
    }

    suspend fun setIntensityPreset(preset: ColorIntensityPreset) {
        dataStore.edit { it[Keys.INTENSITY_PRESET] = preset.name }
    }

    suspend fun setThemeFlavor(flavor: ThemeFlavor) {
        dataStore.edit { it[Keys.THEME_FLAVOR] = flavor.name }
    }

    suspend fun setDynamicColorChromaScale(scale: Float) {
        dataStore.edit { it[Keys.DYNAMIC_COLOR_CHROMA_SCALE] = scale }
    }

    suspend fun setSetupComplete(completed: Boolean) {
        dataStore.edit { it[Keys.IS_SETUP_COMPLETE] = completed }
    }

    // ── Study Preference Updaters ──
    suspend fun setFuriganaMode(mode: FuriganaMode) {
        dataStore.edit { it[Keys.FURIGANA_MODE] = mode.name }
    }

    suspend fun setShowRomaji(enabled: Boolean) {
        dataStore.edit { it[Keys.SHOW_ROMAJI] = enabled }
    }

    suspend fun setDailyGoalWords(count: Int) {
        dataStore.edit { it[Keys.DAILY_GOAL_WORDS] = count }
    }

    suspend fun setSrsAlgorithm(algo: SrsAlgorithm) {
        dataStore.edit { it[Keys.SRS_ALGORITHM] = algo.name }
    }

    suspend fun setDeckOrder(order: DeckOrder) {
        dataStore.edit { it[Keys.DECK_ORDER] = order.name }
    }

    suspend fun setDailyReminderEnabled(enabled: Boolean) {
        dataStore.edit { it[Keys.DAILY_REMINDER_ENABLED] = enabled }
    }

    suspend fun setDailyReminderTime(time: String) {
        dataStore.edit { it[Keys.DAILY_REMINDER_TIME] = time }
    }

    suspend fun resetAllSettings() {
        dataStore.edit { it.clear() }
    }
}
