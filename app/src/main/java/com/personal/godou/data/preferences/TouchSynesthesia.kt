package com.personal.godou.data.preferences

enum class TouchSynesthesia {
    OFF,
    SUBTLE,
    CASSETTE_CLICK,
    MECHANICAL;

    companion object {
        fun fromStorage(value: String?): TouchSynesthesia {
            return try {
                value?.let { valueOf(it) } ?: SUBTLE
            } catch (e: Exception) {
                SUBTLE
            }
        }
    }
}

val TouchSynesthesia.label: String
    get() = when (this) {
        TouchSynesthesia.OFF -> "オフ"
        TouchSynesthesia.SUBTLE -> "控えめ"
        TouchSynesthesia.CASSETTE_CLICK -> "カセット"
        TouchSynesthesia.MECHANICAL -> "メカニカル"
    }

