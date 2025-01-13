package dev.helena.gardenofeden_ccl3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.helena.gardenofeden_ccl3.ui.navigation.AppNavigation
import dev.helena.gardenofeden_ccl3.ui.theme.GardenOfEden_CCL3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GardenOfEden_CCL3Theme {
                AppNavigation()
            }
        }
    }
}
