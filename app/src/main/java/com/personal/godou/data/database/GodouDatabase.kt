package com.personal.godou.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.personal.godou.data.dao.VocabDao
import com.personal.godou.data.entity.VocabEntry

@Database(
    entities = [
        VocabEntry::class
    ],
    version = 1,
    exportSchema = false
)
abstract class GodouDatabase : RoomDatabase() {
    abstract fun vocabDao(): VocabDao
}
