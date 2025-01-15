package dev.helena.gardenofeden_ccl3.data.db

import java.time.LocalDate
import java.time.ZoneOffset

class EntryRepository(private val entryDao: EntryDao) {

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
        return entryDao.getEntryById(entryId)
    }

    suspend fun hasEntryForToday(): Boolean {
        val todayStart = LocalDate.now().atStartOfDay().toEpochSecond(ZoneOffset.UTC)
        val todayEnd = LocalDate.now().plusDays(1).atStartOfDay().toEpochSecond(ZoneOffset.UTC)
        return entryDao.getEntriesInDateRange(todayStart, todayEnd).isNotEmpty()
    }

}
