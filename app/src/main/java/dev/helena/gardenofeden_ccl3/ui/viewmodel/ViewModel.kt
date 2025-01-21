package dev.helena.gardenofeden_ccl3.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.helena.gardenofeden_ccl3.data.db.EntryEntity
import dev.helena.gardenofeden_ccl3.data.db.EntryRepository
import kotlinx.coroutines.launch


class  EntryViewModel(private val repository: EntryRepository) : ViewModel() {

    //liveData to observe entries
    private val _entries = MutableLiveData<List<EntryEntity>>()
    val entries: LiveData<List<EntryEntity>> get() = _entries

    private val _entryDetails = MutableLiveData<EntryEntity>()
    val entryDetails: LiveData<EntryEntity> get() = _entryDetails

    init {
        getAllEntries() // Load entries when the ViewModel is created
    }

    // Check if an entry exists for today
    fun hasEntryForToday(): Boolean {
        return repository.hasEntryForToday()
    }

    // Save the entry date when a new entry is created
    fun saveLastEntryDate(timestamp: Long) {
        repository.saveEntryDate(timestamp)
    }


    fun getAllEntries() {
        viewModelScope.launch {
            _entries.value = repository.getAllEntries()
        }
    }

     fun getEntryById(entryId: Long) {
        viewModelScope.launch {
            _entryDetails.value = repository.getEntryById(entryId)
        }
    }

    fun insertEntry(entry: EntryEntity) {
        viewModelScope.launch {
            repository.insertEntry(entry)
            getAllEntries() // refresh
        }
    }


    fun updateEntry(entry: EntryEntity) {
        viewModelScope.launch {
            repository.updateEntry(entry)
            _entryDetails.value = entry
        }
    }


    fun deleteEntry(entry: EntryEntity) {
        viewModelScope.launch {
            repository.deleteEntry(entry)
            getAllEntries()
        }
    }
}
