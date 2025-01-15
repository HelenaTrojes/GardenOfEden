package dev.helena.gardenofeden_ccl3.data.db

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
}
