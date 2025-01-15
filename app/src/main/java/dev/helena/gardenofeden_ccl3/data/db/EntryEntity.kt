package dev.helena.gardenofeden_ccl3.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entries")
data class EntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val mood: String,
    val question: String,
    val answer: String,
    val date: Long,
)
