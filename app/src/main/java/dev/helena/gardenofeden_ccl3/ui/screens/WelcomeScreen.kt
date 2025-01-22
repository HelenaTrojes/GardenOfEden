package dev.helena.gardenofeden_ccl3.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
            .fillMaxSize()
            .systemBarsPadding() //automatically adds padding to avoid system bars and notches
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

//        Box(
//            modifier = Modifier
////                .wrapContentSize() // Size wraps the content
//        ) {
            // Green Circle (Layer Underneath)
            Image(
                painter = painterResource(id = R.drawable.icon_eden_of_garden),
                contentDescription = "Tulip Image",
                modifier = Modifier
                    .fillMaxWidth(0.60f) // scale down width to 80% of the screen width
                    .aspectRatio(1f)   // maintains the original aspect ratio (1:1 for a square image)
            )

//            Box(
//                modifier = Modifier
//                    .size(250.dp) // Circle size
//                    .align(Alignment.Center) // Align circle to the end
//                    .offset(x = 5.dp, y = 1.dp) // Fine-tune position horizontally
//                    .background(color = Green, shape = CircleShape) // Circle color and shape
//            )

            // "Garden" Text (Layer Above)
            Text(
                text = "Garden",
                fontSize = 45.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .offset(x = -75.dp)
            )
//        }
        Text(
            text = "of",
            fontSize = 45.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 15.dp)
                .offset(x = 2.dp)
        )
        Text(
            text = "Eden",
            fontSize = 45.sp,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 15.dp)
                .offset(x = 65.dp)
        )
        Text(
            text = "Track. Heal. Bloom.",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 30.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    if (entryViewModel.hasEntryForToday()) {
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
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Green,
            )
        ) {
            Text(
                text = "Get started",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Go to your daily mood check-up",
                tint = DarkGreen
            )
        }
    }
}