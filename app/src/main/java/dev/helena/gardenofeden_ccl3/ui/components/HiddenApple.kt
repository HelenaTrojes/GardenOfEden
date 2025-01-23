package dev.helena.gardenofeden_ccl3.ui.components
//
//import android.content.Context
//import android.media.MediaPlayer
//import android.os.Handler
//import android.os.Looper
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlin.math.sqrt
//
//@Composable
//fun HiddenAppleFeature(modifier: Modifier = Modifier) {
//    val context = LocalContext.current
//    var isAppleVisible by remember { mutableStateOf(false) }
//    val coroutineScope = rememberCoroutineScope()
//    val handler = remember { Handler(Looper.getMainLooper()) }
//    var mediaPlayer: MediaPlayer? = remember { null }
//
//    // Shake detection state
//    val shakeThreshold = 15f
//    var lastX by remember { mutableFloatStateOf(0f) }
//    var lastY by remember { mutableFloatStateOf(0f) }
//    var lastZ by remember { mutableFloatStateOf(0f) }
//    var lastTime by remember { mutableLongStateOf(0L) }
//
//    // Start monitoring accelerometer for shakes
//    DisposableEffect(Unit) {
//        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as android.hardware.SensorManager
//        val accelerometer = sensorManager.getDefaultSensor(android.hardware.Sensor.TYPE_ACCELEROMETER)
//        val sensorEventListener = object : android.hardware.SensorEventListener {
//            override fun onSensorChanged(event: android.hardware.SensorEvent?) {
//                event?.let {
//                    val curTime = System.currentTimeMillis()
//                    if (curTime - lastTime > 100) {
//                        val diffTime = (curTime - lastTime).toFloat()
//                        lastTime = curTime
//
//                        val x = it.values[0]
//                        val y = it.values[1]
//                        val z = it.values[2]
//
//                        val speed = sqrt((x - lastX) * (x - lastX) + (y - lastY) * (y - lastY) + (z - lastZ) * (z - lastZ)) / diffTime * 10000
//                        if (speed > shakeThreshold) {
//                            // Trigger the hidden apple feature
//                            if (!isAppleVisible) {
//                                isAppleVisible = true
//                                coroutineScope.launch(Dispatchers.IO) {
//                                    playMusic(context, mediaPlayer)
//                                }
//                                handler.postDelayed({
//                                    isAppleVisible = false
//                                    stopMusic(mediaPlayer)
//                                }, 10000) // Hide after 10 seconds
//                            }
//                        }
//
//                        lastX = x
//                        lastY = y
//                        lastZ = z
//                    }
//                }
//            }
//
//            override fun onAccuracyChanged(sensor: android.hardware.Sensor?, accuracy: Int) {}
//        }
//
//        sensorManager.registerListener(sensorEventListener, accelerometer, android.hardware.SensorManager.SENSOR_DELAY_UI)
//
//        onDispose {
//            sensorManager.unregisterListener(sensorEventListener)
//            stopMusic(mediaPlayer)
//        }
//    }
//
//    // UI for apple visibility
//    Box(modifier = Modifier.fillMaxSize()) {
//        if (isAppleVisible) {
//            Image(
//                painter = painterResource(id = R.drawable.apple), // Replace with your apple image resource
//                contentDescription = "Hidden Apple",
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
//    }
//}
//
//fun playMusic(context: Context, mediaPlayer: MediaPlayer?): MediaPlayer {
//    val mp = mediaPlayer ?: MediaPlayer.create(context, R.raw.hiddenapple)
//    mp.start()
//    return mp
//}
//
//fun stopMusic(mediaPlayer: MediaPlayer?) {
//    mediaPlayer?.stop()
//    mediaPlayer?.release()
//}
