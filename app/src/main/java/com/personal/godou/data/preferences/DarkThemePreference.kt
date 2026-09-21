package com.personal.godou.data.preferences

enum class DarkThemePreference(val label: String) {
    SYSTEM("システム標準"),
    LIGHT("ライト"),
    DARK("ダーク");

    fun isDark(systemIsDark: Boolean): Boolean = when (this) {
        SYSTEM -> systemIsDark
        LIGHT -> false
        DARK -> true
    }

    companion object {
        fun fromStorage(value: String?): DarkThemePreference =
            entries.find { it.name == value } ?: SYSTEM
    }
}
