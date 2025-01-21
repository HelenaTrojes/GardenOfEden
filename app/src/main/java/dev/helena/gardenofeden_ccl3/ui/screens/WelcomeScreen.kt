package dev.helena.gardenofeden_ccl3.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.helena.gardenofeden_ccl3.R
import dev.helena.gardenofeden_ccl3.ui.viewmodel.EntryViewModel
import dev.helena.gardenofeden_ccl3.ui.theme.DarkGreen
import dev.helena.gardenofeden_ccl3.ui.theme.Green
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(navController: NavHostController, entryViewModel: EntryViewModel) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = "Garden",
            fontSize = 40.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Text(
            text = "Track. Heal. Bloom.",
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
            color = DarkGreen,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.tulip),
            contentDescription = "Tulip Image",
            modifier = Modifier
                .fillMaxWidth(0.8f) // scale down width to 80% of the screen width
                .aspectRatio(1f)   // maintains the original aspect ratio (1:1 for a square image)
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    if(entryViewModel.hasEntryForToday()) {
                        navController.navigate("home")
                    } else {
                        navController.navigate("landing")
                    }
                }
            },
            modifier = Modifier
                .padding(top = 30.dp)
                .height(50.dp)
                .padding(horizontal = 12.dp),
            shape = androidx.compose.foundation.shape.CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor  = Green,
            )
        ) {
            Text(
                text = "Get started",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Go to your daily mood check-up",
                tint = Color.Black
            )
        }
    }
}

