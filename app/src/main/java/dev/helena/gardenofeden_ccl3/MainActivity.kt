package dev.helena.gardenofeden_ccl3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import dev.helena.gardenofeden_ccl3.data.db.AppDatabase
import dev.helena.gardenofeden_ccl3.data.db.EntryRepository
import dev.helena.gardenofeden_ccl3.ui.ViewModel.EntryViewModel
import dev.helena.gardenofeden_ccl3.ui.ViewModel.EntryViewModelFactory
import dev.helena.gardenofeden_ccl3.ui.navigation.AppNavigation
import dev.helena.gardenofeden_ccl3.ui.theme.GardenOfEden_CCL3Theme

class MainActivity : ComponentActivity() {
    private lateinit var entryViewModel: EntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewModel
        val entryDao = AppDatabase.getDatabase(applicationContext).entryDao()
        val repository = EntryRepository(entryDao)
        val viewModelFactory = EntryViewModelFactory(repository)
        entryViewModel = ViewModelProvider(this, viewModelFactory)[EntryViewModel::class.java]

        // Set up UI
        enableEdgeToEdge()
        setContent {
            GardenOfEden_CCL3Theme {
                // Pass the viewModel here
                AppNavigation()
            }
        }
    }
}
