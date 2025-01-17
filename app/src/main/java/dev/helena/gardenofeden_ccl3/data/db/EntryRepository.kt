package dev.helena.gardenofeden_ccl3.data.db

import PreferenceManager
import java.time.LocalDate
import java.time.ZoneOffset

class EntryRepository(private val entryDao: EntryDao, private val preferenceManager: PreferenceManager) {

    suspend fun insertEntry(entry: EntryEntity) {
        entryDao.insert(entry)
    }

    suspend fun updateEntry(entry: EntryEntity) {
        entryDao.update(entry)
    }

    suspend fun deleteEntry(entry: EntryEntity) {
        entryDao.delete(entry)
    }

    suspend fun getAllEntries(): List<EntryEntity> {
        return entryDao.getAllEntries()
    }

    suspend fun getEntryById(entryId: Long): EntryEntity? {
        val entry = entryDao.getEntryById(entryId)
        return entry
    }

    // Check if an entry exists for today
    fun hasEntryForToday(): Boolean {
        val todayStart = LocalDate.now().atStartOfDay().toEpochSecond(ZoneOffset.UTC) * 1000 // Milliseconds
        val lastEntryDate = preferenceManager.getLastEntryDate()
        return lastEntryDate >= todayStart
    }

    // Save the last entry date when a new entry is created
    fun saveEntryDate(timestamp: Long) {
        preferenceManager.saveLastEntryDate(timestamp)
    }

}
