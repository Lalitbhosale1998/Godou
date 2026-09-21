package com.personal.godou.`data`.database

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.personal.godou.`data`.dao.VocabDao
import com.personal.godou.`data`.dao.VocabDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class GodouDatabase_Impl : GodouDatabase() {
  private val _vocabDao: Lazy<VocabDao> = lazy {
    VocabDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1,
        "61dae33236373a1d0ab1de34b81c9c62", "67b0900f67034490449ac55f05b5ad35") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `vocab_entries` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `kanjiWord` TEXT NOT NULL, `furiganaReading` TEXT NOT NULL, `meaning` TEXT NOT NULL, `category` TEXT NOT NULL, `subCategory` TEXT NOT NULL, `studyTag` TEXT NOT NULL, `exampleSentence` TEXT NOT NULL, `isMastered` INTEGER NOT NULL, `isStarred` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '61dae33236373a1d0ab1de34b81c9c62')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `vocab_entries`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsVocabEntries: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsVocabEntries.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVocabEntries.put("kanjiWord", TableInfo.Column("kanjiWord", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVocabEntries.put("furiganaReading", TableInfo.Column("furiganaReading", "TEXT",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsVocabEntries.put("meaning", TableInfo.Column("meaning", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVocabEntries.put("category", TableInfo.Column("category", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVocabEntries.put("subCategory", TableInfo.Column("subCategory", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsVocabEntries.put("studyTag", TableInfo.Column("studyTag", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVocabEntries.put("exampleSentence", TableInfo.Column("exampleSentence", "TEXT",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsVocabEntries.put("isMastered", TableInfo.Column("isMastered", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsVocabEntries.put("isStarred", TableInfo.Column("isStarred", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsVocabEntries.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysVocabEntries: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesVocabEntries: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoVocabEntries: TableInfo = TableInfo("vocab_entries", _columnsVocabEntries,
            _foreignKeysVocabEntries, _indicesVocabEntries)
        val _existingVocabEntries: TableInfo = read(connection, "vocab_entries")
        if (!_infoVocabEntries.equals(_existingVocabEntries)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |vocab_entries(com.personal.godou.data.entity.VocabEntry).
              | Expected:
              |""".trimMargin() + _infoVocabEntries + """
              |
              | Found:
              |""".trimMargin() + _existingVocabEntries)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "vocab_entries")
  }

  public override fun clearAllTables() {
    super.performClear(false, "vocab_entries")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(VocabDao::class, VocabDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun vocabDao(): VocabDao = _vocabDao.value
}
