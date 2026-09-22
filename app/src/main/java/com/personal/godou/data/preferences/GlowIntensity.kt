package com.personal.godou.data.preferences

enum class GlowIntensity {
    OFF,
    SUBTLE,
    NEON,
    PULSING;

    companion object {
        fun fromStorage(value: String?): GlowIntensity {
            return try {
                value?.let { valueOf(it) } ?: SUBTLE
            } catch (e: Exception) {
                SUBTLE
            }
        }
    }
}

val GlowIntensity.label: String
    get() = when (this) {
        GlowIntensity.OFF -> "オフ"
        GlowIntensity.SUBTLE -> "控えめ"
        GlowIntensity.NEON -> "ネオン"
        GlowIntensity.PULSING -> "パルス"
    }

