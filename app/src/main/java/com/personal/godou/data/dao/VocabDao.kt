package com.personal.godou.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.personal.godou.data.entity.VocabEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface VocabDao {
    @Query("SELECT * FROM vocab_entries ORDER BY id ASC")
    fun getAllEntries(): Flow<List<VocabEntry>>

    @Query("SELECT * FROM vocab_entries WHERE category = :category ORDER BY id ASC")
    fun getEntriesByCategory(category: String): Flow<List<VocabEntry>>

    @Query("SELECT * FROM vocab_entries WHERE studyTag = :studyTag ORDER BY id ASC")
    fun getEntriesByStudyTag(studyTag: String): Flow<List<VocabEntry>>

    @Query("SELECT * FROM vocab_entries WHERE isStarred = 1 ORDER BY id ASC")
    fun getStarredEntries(): Flow<List<VocabEntry>>

    @Query("SELECT * FROM vocab_entries WHERE isMastered = 1 ORDER BY id ASC")
    fun getMasteredEntries(): Flow<List<VocabEntry>>

    @Query("SELECT * FROM vocab_entries WHERE kanjiWord LIKE '%' || :query || '%' OR furiganaReading LIKE '%' || :query || '%' OR meaning LIKE '%' || :query || '%' ORDER BY id ASC")
    fun searchEntries(query: String): Flow<List<VocabEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: VocabEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entries: List<VocabEntry>)

    @Update
    suspend fun updateEntry(entry: VocabEntry)

    @Delete
    suspend fun deleteEntry(entry: VocabEntry)

    @Query("DELETE FROM vocab_entries WHERE id = :id")
    suspend fun deleteEntryById(id: Int)

    @Query("SELECT COUNT(*) FROM vocab_entries")
    suspend fun getEntryCount(): Int
}
