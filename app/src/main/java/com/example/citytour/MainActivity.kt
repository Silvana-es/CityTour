package com.example.citytour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.citytour.audio.ReproductorAudio
import com.example.citytour.ui.screens.MonumentoScreen
import com.example.citytour.ui.theme.CityTourTheme

class MainActivity : ComponentActivity() {

    private lateinit var reproductor: ReproductorAudio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // El reproductor carga sus audios desde res/raw
        reproductor = ReproductorAudio(this)

        setContent {
            CityTourTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.systemBarsPadding()) {
                        MonumentoScreen(reproductor)
                    }
                }
            }
        }
    }

    // Si la app pasa a segundo plano, se pausa el audio
    override fun onPause() {
        super.onPause()
        reproductor.pausar()
    }

    // Se libera la memoria del reproductor al cerrar la pantalla
    override fun onDestroy() {
        super.onDestroy()
        reproductor.liberar()
    }
}
