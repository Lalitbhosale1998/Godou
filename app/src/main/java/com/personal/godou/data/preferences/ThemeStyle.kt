package com.personal.godou.data.preferences

enum class ThemeStyle {
    DEFAULT;

    companion object {
        fun fromStorage(value: String?): ThemeStyle {
            return try {
                value?.let { valueOf(it) } ?: DEFAULT
            } catch (e: Exception) {
                DEFAULT
            }
        }
    }
}

