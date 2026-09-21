package com.personal.godou.data.repository

import com.personal.godou.data.dao.VocabDao
import com.personal.godou.data.entity.JLPTN1SeedData
import com.personal.godou.data.entity.VocabEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VocabRepository @Inject constructor(
    private val vocabDao: VocabDao
) {
    val allEntries: Flow<List<VocabEntry>> = vocabDao.getAllEntries()

    suspend fun checkAndSeedDatabase() {
        if (vocabDao.getEntryCount() == 0) {
            vocabDao.insertAll(JLPTN1SeedData.week1Day1Entries)
        }
    }

    fun getEntriesByCategory(category: String): Flow<List<VocabEntry>> =
        vocabDao.getEntriesByCategory(category)

    fun getEntriesByStudyTag(studyTag: String): Flow<List<VocabEntry>> =
        vocabDao.getEntriesByStudyTag(studyTag)

    fun getStarredEntries(): Flow<List<VocabEntry>> =
        vocabDao.getStarredEntries()

    fun getMasteredEntries(): Flow<List<VocabEntry>> =
        vocabDao.getMasteredEntries()

    fun searchEntries(query: String): Flow<List<VocabEntry>> =
        vocabDao.searchEntries(query)

    suspend fun insertEntry(entry: VocabEntry) {
        vocabDao.insertEntry(entry)
    }

    suspend fun updateEntry(entry: VocabEntry) {
        vocabDao.updateEntry(entry)
    }

    suspend fun deleteEntry(entry: VocabEntry) {
        vocabDao.deleteEntry(entry)
    }

    suspend fun deleteEntryById(id: Int) {
        vocabDao.deleteEntryById(id)
    }

    suspend fun getEntryCount(): Int = vocabDao.getEntryCount()
}
