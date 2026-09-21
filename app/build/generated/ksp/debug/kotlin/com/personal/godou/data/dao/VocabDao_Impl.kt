package com.personal.godou.`data`.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.personal.godou.`data`.entity.VocabEntry
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class VocabDao_Impl(
  __db: RoomDatabase,
) : VocabDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfVocabEntry: EntityInsertAdapter<VocabEntry>

  private val __deleteAdapterOfVocabEntry: EntityDeleteOrUpdateAdapter<VocabEntry>

  private val __updateAdapterOfVocabEntry: EntityDeleteOrUpdateAdapter<VocabEntry>
  init {
    this.__db = __db
    this.__insertAdapterOfVocabEntry = object : EntityInsertAdapter<VocabEntry>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `vocab_entries` (`id`,`kanjiWord`,`furiganaReading`,`meaning`,`category`,`subCategory`,`studyTag`,`exampleSentence`,`isMastered`,`isStarred`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: VocabEntry) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.kanjiWord)
        statement.bindText(3, entity.furiganaReading)
        statement.bindText(4, entity.meaning)
        statement.bindText(5, entity.category)
        statement.bindText(6, entity.subCategory)
        statement.bindText(7, entity.studyTag)
        statement.bindText(8, entity.exampleSentence)
        val _tmp: Int = if (entity.isMastered) 1 else 0
        statement.bindLong(9, _tmp.toLong())
        val _tmp_1: Int = if (entity.isStarred) 1 else 0
        statement.bindLong(10, _tmp_1.toLong())
        statement.bindLong(11, entity.timestamp)
      }
    }
    this.__deleteAdapterOfVocabEntry = object : EntityDeleteOrUpdateAdapter<VocabEntry>() {
      protected override fun createQuery(): String = "DELETE FROM `vocab_entries` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: VocabEntry) {
        statement.bindLong(1, entity.id.toLong())
      }
    }
    this.__updateAdapterOfVocabEntry = object : EntityDeleteOrUpdateAdapter<VocabEntry>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `vocab_entries` SET `id` = ?,`kanjiWord` = ?,`furiganaReading` = ?,`meaning` = ?,`category` = ?,`subCategory` = ?,`studyTag` = ?,`exampleSentence` = ?,`isMastered` = ?,`isStarred` = ?,`timestamp` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: VocabEntry) {
        statement.bindLong(1, entity.id.toLong())
        statement.bindText(2, entity.kanjiWord)
        statement.bindText(3, entity.furiganaReading)
        statement.bindText(4, entity.meaning)
        statement.bindText(5, entity.category)
        statement.bindText(6, entity.subCategory)
        statement.bindText(7, entity.studyTag)
        statement.bindText(8, entity.exampleSentence)
        val _tmp: Int = if (entity.isMastered) 1 else 0
        statement.bindLong(9, _tmp.toLong())
        val _tmp_1: Int = if (entity.isStarred) 1 else 0
        statement.bindLong(10, _tmp_1.toLong())
        statement.bindLong(11, entity.timestamp)
        statement.bindLong(12, entity.id.toLong())
      }
    }
  }

  public override suspend fun insertEntry(entry: VocabEntry): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfVocabEntry.insert(_connection, entry)
  }

  public override suspend fun insertAll(entries: List<VocabEntry>): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfVocabEntry.insert(_connection, entries)
  }

  public override suspend fun deleteEntry(entry: VocabEntry): Unit = performSuspending(__db, false,
      true) { _connection ->
    __deleteAdapterOfVocabEntry.handle(_connection, entry)
  }

  public override suspend fun updateEntry(entry: VocabEntry): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfVocabEntry.handle(_connection, entry)
  }

  public override fun getAllEntries(): Flow<List<VocabEntry>> {
    val _sql: String = "SELECT * FROM vocab_entries ORDER BY id ASC"
    return createFlow(__db, false, arrayOf("vocab_entries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfKanjiWord: Int = getColumnIndexOrThrow(_stmt, "kanjiWord")
        val _columnIndexOfFuriganaReading: Int = getColumnIndexOrThrow(_stmt, "furiganaReading")
        val _columnIndexOfMeaning: Int = getColumnIndexOrThrow(_stmt, "meaning")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfSubCategory: Int = getColumnIndexOrThrow(_stmt, "subCategory")
        val _columnIndexOfStudyTag: Int = getColumnIndexOrThrow(_stmt, "studyTag")
        val _columnIndexOfExampleSentence: Int = getColumnIndexOrThrow(_stmt, "exampleSentence")
        val _columnIndexOfIsMastered: Int = getColumnIndexOrThrow(_stmt, "isMastered")
        val _columnIndexOfIsStarred: Int = getColumnIndexOrThrow(_stmt, "isStarred")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<VocabEntry> = mutableListOf()
        while (_stmt.step()) {
          val _item: VocabEntry
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpKanjiWord: String
          _tmpKanjiWord = _stmt.getText(_columnIndexOfKanjiWord)
          val _tmpFuriganaReading: String
          _tmpFuriganaReading = _stmt.getText(_columnIndexOfFuriganaReading)
          val _tmpMeaning: String
          _tmpMeaning = _stmt.getText(_columnIndexOfMeaning)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpSubCategory: String
          _tmpSubCategory = _stmt.getText(_columnIndexOfSubCategory)
          val _tmpStudyTag: String
          _tmpStudyTag = _stmt.getText(_columnIndexOfStudyTag)
          val _tmpExampleSentence: String
          _tmpExampleSentence = _stmt.getText(_columnIndexOfExampleSentence)
          val _tmpIsMastered: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsMastered).toInt()
          _tmpIsMastered = _tmp != 0
          val _tmpIsStarred: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsStarred).toInt()
          _tmpIsStarred = _tmp_1 != 0
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item =
              VocabEntry(_tmpId,_tmpKanjiWord,_tmpFuriganaReading,_tmpMeaning,_tmpCategory,_tmpSubCategory,_tmpStudyTag,_tmpExampleSentence,_tmpIsMastered,_tmpIsStarred,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getEntriesByCategory(category: String): Flow<List<VocabEntry>> {
    val _sql: String = "SELECT * FROM vocab_entries WHERE category = ? ORDER BY id ASC"
    return createFlow(__db, false, arrayOf("vocab_entries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, category)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfKanjiWord: Int = getColumnIndexOrThrow(_stmt, "kanjiWord")
        val _columnIndexOfFuriganaReading: Int = getColumnIndexOrThrow(_stmt, "furiganaReading")
        val _columnIndexOfMeaning: Int = getColumnIndexOrThrow(_stmt, "meaning")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfSubCategory: Int = getColumnIndexOrThrow(_stmt, "subCategory")
        val _columnIndexOfStudyTag: Int = getColumnIndexOrThrow(_stmt, "studyTag")
        val _columnIndexOfExampleSentence: Int = getColumnIndexOrThrow(_stmt, "exampleSentence")
        val _columnIndexOfIsMastered: Int = getColumnIndexOrThrow(_stmt, "isMastered")
        val _columnIndexOfIsStarred: Int = getColumnIndexOrThrow(_stmt, "isStarred")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<VocabEntry> = mutableListOf()
        while (_stmt.step()) {
          val _item: VocabEntry
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpKanjiWord: String
          _tmpKanjiWord = _stmt.getText(_columnIndexOfKanjiWord)
          val _tmpFuriganaReading: String
          _tmpFuriganaReading = _stmt.getText(_columnIndexOfFuriganaReading)
          val _tmpMeaning: String
          _tmpMeaning = _stmt.getText(_columnIndexOfMeaning)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpSubCategory: String
          _tmpSubCategory = _stmt.getText(_columnIndexOfSubCategory)
          val _tmpStudyTag: String
          _tmpStudyTag = _stmt.getText(_columnIndexOfStudyTag)
          val _tmpExampleSentence: String
          _tmpExampleSentence = _stmt.getText(_columnIndexOfExampleSentence)
          val _tmpIsMastered: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsMastered).toInt()
          _tmpIsMastered = _tmp != 0
          val _tmpIsStarred: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsStarred).toInt()
          _tmpIsStarred = _tmp_1 != 0
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item =
              VocabEntry(_tmpId,_tmpKanjiWord,_tmpFuriganaReading,_tmpMeaning,_tmpCategory,_tmpSubCategory,_tmpStudyTag,_tmpExampleSentence,_tmpIsMastered,_tmpIsStarred,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getEntriesByStudyTag(studyTag: String): Flow<List<VocabEntry>> {
    val _sql: String = "SELECT * FROM vocab_entries WHERE studyTag = ? ORDER BY id ASC"
    return createFlow(__db, false, arrayOf("vocab_entries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, studyTag)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfKanjiWord: Int = getColumnIndexOrThrow(_stmt, "kanjiWord")
        val _columnIndexOfFuriganaReading: Int = getColumnIndexOrThrow(_stmt, "furiganaReading")
        val _columnIndexOfMeaning: Int = getColumnIndexOrThrow(_stmt, "meaning")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfSubCategory: Int = getColumnIndexOrThrow(_stmt, "subCategory")
        val _columnIndexOfStudyTag: Int = getColumnIndexOrThrow(_stmt, "studyTag")
        val _columnIndexOfExampleSentence: Int = getColumnIndexOrThrow(_stmt, "exampleSentence")
        val _columnIndexOfIsMastered: Int = getColumnIndexOrThrow(_stmt, "isMastered")
        val _columnIndexOfIsStarred: Int = getColumnIndexOrThrow(_stmt, "isStarred")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<VocabEntry> = mutableListOf()
        while (_stmt.step()) {
          val _item: VocabEntry
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpKanjiWord: String
          _tmpKanjiWord = _stmt.getText(_columnIndexOfKanjiWord)
          val _tmpFuriganaReading: String
          _tmpFuriganaReading = _stmt.getText(_columnIndexOfFuriganaReading)
          val _tmpMeaning: String
          _tmpMeaning = _stmt.getText(_columnIndexOfMeaning)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpSubCategory: String
          _tmpSubCategory = _stmt.getText(_columnIndexOfSubCategory)
          val _tmpStudyTag: String
          _tmpStudyTag = _stmt.getText(_columnIndexOfStudyTag)
          val _tmpExampleSentence: String
          _tmpExampleSentence = _stmt.getText(_columnIndexOfExampleSentence)
          val _tmpIsMastered: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsMastered).toInt()
          _tmpIsMastered = _tmp != 0
          val _tmpIsStarred: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsStarred).toInt()
          _tmpIsStarred = _tmp_1 != 0
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item =
              VocabEntry(_tmpId,_tmpKanjiWord,_tmpFuriganaReading,_tmpMeaning,_tmpCategory,_tmpSubCategory,_tmpStudyTag,_tmpExampleSentence,_tmpIsMastered,_tmpIsStarred,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getStarredEntries(): Flow<List<VocabEntry>> {
    val _sql: String = "SELECT * FROM vocab_entries WHERE isStarred = 1 ORDER BY id ASC"
    return createFlow(__db, false, arrayOf("vocab_entries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfKanjiWord: Int = getColumnIndexOrThrow(_stmt, "kanjiWord")
        val _columnIndexOfFuriganaReading: Int = getColumnIndexOrThrow(_stmt, "furiganaReading")
        val _columnIndexOfMeaning: Int = getColumnIndexOrThrow(_stmt, "meaning")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfSubCategory: Int = getColumnIndexOrThrow(_stmt, "subCategory")
        val _columnIndexOfStudyTag: Int = getColumnIndexOrThrow(_stmt, "studyTag")
        val _columnIndexOfExampleSentence: Int = getColumnIndexOrThrow(_stmt, "exampleSentence")
        val _columnIndexOfIsMastered: Int = getColumnIndexOrThrow(_stmt, "isMastered")
        val _columnIndexOfIsStarred: Int = getColumnIndexOrThrow(_stmt, "isStarred")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<VocabEntry> = mutableListOf()
        while (_stmt.step()) {
          val _item: VocabEntry
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpKanjiWord: String
          _tmpKanjiWord = _stmt.getText(_columnIndexOfKanjiWord)
          val _tmpFuriganaReading: String
          _tmpFuriganaReading = _stmt.getText(_columnIndexOfFuriganaReading)
          val _tmpMeaning: String
          _tmpMeaning = _stmt.getText(_columnIndexOfMeaning)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpSubCategory: String
          _tmpSubCategory = _stmt.getText(_columnIndexOfSubCategory)
          val _tmpStudyTag: String
          _tmpStudyTag = _stmt.getText(_columnIndexOfStudyTag)
          val _tmpExampleSentence: String
          _tmpExampleSentence = _stmt.getText(_columnIndexOfExampleSentence)
          val _tmpIsMastered: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsMastered).toInt()
          _tmpIsMastered = _tmp != 0
          val _tmpIsStarred: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsStarred).toInt()
          _tmpIsStarred = _tmp_1 != 0
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item =
              VocabEntry(_tmpId,_tmpKanjiWord,_tmpFuriganaReading,_tmpMeaning,_tmpCategory,_tmpSubCategory,_tmpStudyTag,_tmpExampleSentence,_tmpIsMastered,_tmpIsStarred,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getMasteredEntries(): Flow<List<VocabEntry>> {
    val _sql: String = "SELECT * FROM vocab_entries WHERE isMastered = 1 ORDER BY id ASC"
    return createFlow(__db, false, arrayOf("vocab_entries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfKanjiWord: Int = getColumnIndexOrThrow(_stmt, "kanjiWord")
        val _columnIndexOfFuriganaReading: Int = getColumnIndexOrThrow(_stmt, "furiganaReading")
        val _columnIndexOfMeaning: Int = getColumnIndexOrThrow(_stmt, "meaning")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfSubCategory: Int = getColumnIndexOrThrow(_stmt, "subCategory")
        val _columnIndexOfStudyTag: Int = getColumnIndexOrThrow(_stmt, "studyTag")
        val _columnIndexOfExampleSentence: Int = getColumnIndexOrThrow(_stmt, "exampleSentence")
        val _columnIndexOfIsMastered: Int = getColumnIndexOrThrow(_stmt, "isMastered")
        val _columnIndexOfIsStarred: Int = getColumnIndexOrThrow(_stmt, "isStarred")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<VocabEntry> = mutableListOf()
        while (_stmt.step()) {
          val _item: VocabEntry
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpKanjiWord: String
          _tmpKanjiWord = _stmt.getText(_columnIndexOfKanjiWord)
          val _tmpFuriganaReading: String
          _tmpFuriganaReading = _stmt.getText(_columnIndexOfFuriganaReading)
          val _tmpMeaning: String
          _tmpMeaning = _stmt.getText(_columnIndexOfMeaning)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpSubCategory: String
          _tmpSubCategory = _stmt.getText(_columnIndexOfSubCategory)
          val _tmpStudyTag: String
          _tmpStudyTag = _stmt.getText(_columnIndexOfStudyTag)
          val _tmpExampleSentence: String
          _tmpExampleSentence = _stmt.getText(_columnIndexOfExampleSentence)
          val _tmpIsMastered: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsMastered).toInt()
          _tmpIsMastered = _tmp != 0
          val _tmpIsStarred: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsStarred).toInt()
          _tmpIsStarred = _tmp_1 != 0
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item =
              VocabEntry(_tmpId,_tmpKanjiWord,_tmpFuriganaReading,_tmpMeaning,_tmpCategory,_tmpSubCategory,_tmpStudyTag,_tmpExampleSentence,_tmpIsMastered,_tmpIsStarred,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchEntries(query: String): Flow<List<VocabEntry>> {
    val _sql: String =
        "SELECT * FROM vocab_entries WHERE kanjiWord LIKE '%' || ? || '%' OR furiganaReading LIKE '%' || ? || '%' OR meaning LIKE '%' || ? || '%' ORDER BY id ASC"
    return createFlow(__db, false, arrayOf("vocab_entries")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        _argIndex = 3
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfKanjiWord: Int = getColumnIndexOrThrow(_stmt, "kanjiWord")
        val _columnIndexOfFuriganaReading: Int = getColumnIndexOrThrow(_stmt, "furiganaReading")
        val _columnIndexOfMeaning: Int = getColumnIndexOrThrow(_stmt, "meaning")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfSubCategory: Int = getColumnIndexOrThrow(_stmt, "subCategory")
        val _columnIndexOfStudyTag: Int = getColumnIndexOrThrow(_stmt, "studyTag")
        val _columnIndexOfExampleSentence: Int = getColumnIndexOrThrow(_stmt, "exampleSentence")
        val _columnIndexOfIsMastered: Int = getColumnIndexOrThrow(_stmt, "isMastered")
        val _columnIndexOfIsStarred: Int = getColumnIndexOrThrow(_stmt, "isStarred")
        val _columnIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<VocabEntry> = mutableListOf()
        while (_stmt.step()) {
          val _item: VocabEntry
          val _tmpId: Int
          _tmpId = _stmt.getLong(_columnIndexOfId).toInt()
          val _tmpKanjiWord: String
          _tmpKanjiWord = _stmt.getText(_columnIndexOfKanjiWord)
          val _tmpFuriganaReading: String
          _tmpFuriganaReading = _stmt.getText(_columnIndexOfFuriganaReading)
          val _tmpMeaning: String
          _tmpMeaning = _stmt.getText(_columnIndexOfMeaning)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpSubCategory: String
          _tmpSubCategory = _stmt.getText(_columnIndexOfSubCategory)
          val _tmpStudyTag: String
          _tmpStudyTag = _stmt.getText(_columnIndexOfStudyTag)
          val _tmpExampleSentence: String
          _tmpExampleSentence = _stmt.getText(_columnIndexOfExampleSentence)
          val _tmpIsMastered: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsMastered).toInt()
          _tmpIsMastered = _tmp != 0
          val _tmpIsStarred: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsStarred).toInt()
          _tmpIsStarred = _tmp_1 != 0
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_columnIndexOfTimestamp)
          _item =
              VocabEntry(_tmpId,_tmpKanjiWord,_tmpFuriganaReading,_tmpMeaning,_tmpCategory,_tmpSubCategory,_tmpStudyTag,_tmpExampleSentence,_tmpIsMastered,_tmpIsStarred,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getEntryCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM vocab_entries"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteEntryById(id: Int) {
    val _sql: String = "DELETE FROM vocab_entries WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id.toLong())
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
