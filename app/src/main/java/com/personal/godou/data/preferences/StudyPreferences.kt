package com.personal.godou.data.preferences

enum class FuriganaMode(val label: String) {
    ALWAYS_SHOW("常に表示"),
    HIDE_TAP_TO_REVEAL("タップで表示"),
    ADAPT_JLPT("JLPTレベル適応");

    companion object {
        fun fromStorage(name: String?): FuriganaMode {
            return entries.firstOrNull { it.name == name } ?: ALWAYS_SHOW
        }
    }
}

enum class SrsAlgorithm(val label: String) {
    FSRS_ANKI("FSRS / Anki方式"),
    LEITNER("標準Leitner方式");

    companion object {
        fun fromStorage(name: String?): SrsAlgorithm {
            return entries.firstOrNull { it.name == name } ?: FSRS_ANKI
        }
    }
}

enum class DeckOrder(val label: String) {
    SHUFFLE("シャッフル"),
    DIFFICULTY("難易度順"),
    DUE_DATE("復習期日順"),
    NEWEST_FIRST("新着順");

    companion object {
        fun fromStorage(name: String?): DeckOrder {
            return entries.firstOrNull { it.name == name } ?: DUE_DATE
        }
    }
}

data class StudyPreferences(
    val furiganaMode: FuriganaMode = FuriganaMode.ALWAYS_SHOW,
    val showRomaji: Boolean = false,
    val dailyGoalWords: Int = 4,
    val srsAlgorithm: SrsAlgorithm = SrsAlgorithm.FSRS_ANKI,
    val deckOrder: DeckOrder = DeckOrder.DUE_DATE,
    val dailyReminderEnabled: Boolean = true,
    val dailyReminderTime: String = "20:00"
)
