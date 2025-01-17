import android.content.Context

//plan first was to do that with data storage but it didn't work wo I used sharedPreferences
class PreferenceManager(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Save the last entry date
    fun saveLastEntryDate(timestamp: Long) {
        sharedPreferences.edit().putLong("last_entry_date", timestamp).apply()
    }

    // Retrieve the last entry date (default to 0 if not found)
    fun getLastEntryDate(): Long {
        return sharedPreferences.getLong("last_entry_date", 0L)
    }
}
