package com.example.citytour.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class EstadoAudio { DETENIDO, REPRODUCIENDO, PAUSADO }

class ReproductorAudio(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var recursoActual: Int? = null

    // Al cambiar estos valores, la pantalla se actualiza sola
    var estado by mutableStateOf(EstadoAudio.DETENIDO)
        private set
    var posicionMs by mutableIntStateOf(0)
        private set
    var duracionMs by mutableIntStateOf(0)
        private set
    var volumen by mutableFloatStateOf(1f)
        private set

    // Carga un audio desde res/raw. Si ya estaba cargado no hace nada.
    // Al cambiar de audio se libera el anterior, así solo hay un MediaPlayer en memoria.
    fun cargar(recursoAudio: Int) {
        if (recursoAudio == recursoActual && mediaPlayer != null) return
        liberar()

        val atributos = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()

        val mp = MediaPlayer.create(
            context,
            recursoAudio,
            atributos,
            AudioManager.AUDIO_SESSION_ID_GENERATE
        ) ?: return

        mp.setVolume(volumen, volumen)
        // Cuando el audio termina, el estado vuelve a detenido
        mp.setOnCompletionListener {
            estado = EstadoAudio.DETENIDO
            posicionMs = 0
        }

        mediaPlayer = mp
        recursoActual = recursoAudio
        duracionMs = mp.duration
        posicionMs = 0
        estado = EstadoAudio.DETENIDO
    }

    // Play y reanudar: desde detenido empieza, desde pausa continúa donde se quedó
    fun reproducir() {
        val mp = mediaPlayer ?: return
        if (estado != EstadoAudio.REPRODUCIENDO) {
            mp.start()
            estado = EstadoAudio.REPRODUCIENDO
        }
    }

    fun pausar() {
        val mp = mediaPlayer ?: return
        if (estado == EstadoAudio.REPRODUCIENDO) {
            mp.pause()
            sincronizarPosicion()
            estado = EstadoAudio.PAUSADO
        }
    }

    // Stop: pausa y regresa al inicio, dejando el audio listo para un nuevo play
    fun detener() {
        val mp = mediaPlayer ?: return
        if (estado != EstadoAudio.DETENIDO) {
            mp.pause()
            mp.seekTo(0)
            posicionMs = 0
            estado = EstadoAudio.DETENIDO
        }
    }

    fun cambiarVolumen(nuevoVolumen: Float) {
        volumen = nuevoVolumen.coerceIn(0f, 1f)
        mediaPlayer?.setVolume(volumen, volumen)
    }

    // Lee la posición real del MediaPlayer para la barra de progreso
    fun sincronizarPosicion() {
        mediaPlayer?.let { posicionMs = it.currentPosition }
    }

    // Se libera la memoria del reproductor
    fun liberar() {
        mediaPlayer?.release()
        mediaPlayer = null
        recursoActual = null
        posicionMs = 0
        duracionMs = 0
        estado = EstadoAudio.DETENIDO
    }
}
