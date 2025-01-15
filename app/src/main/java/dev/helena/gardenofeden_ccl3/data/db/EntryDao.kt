package dev.helena.gardenofeden_ccl3.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

    @Dao
    interface EntryDao {

        // Insert a new entry
        @Insert
        suspend fun insert(entry: EntryEntity)

        // Update an existing entry
        @Update
        suspend fun update(entry: EntryEntity)

        // Delete an entry
        @Delete
        suspend fun delete(entry: EntryEntity)

        // Get all entries
        @Query("SELECT * FROM entries ORDER BY date DESC")
        suspend fun getAllEntries(): List<EntryEntity>

        // Get an entry by ID
        @Query("SELECT * FROM entries WHERE id = :entryId LIMIT 1")
        suspend fun getEntryById(entryId: Long): EntryEntity?

        @Query("SELECT * FROM entries WHERE date >= :start AND date < :end")
        suspend fun getEntriesInDateRange(start: Long, end: Long): List<EntryEntity>
    }
