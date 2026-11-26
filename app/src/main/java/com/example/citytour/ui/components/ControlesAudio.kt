package com.example.citytour.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.citytour.R
import com.example.citytour.audio.EstadoAudio
import java.util.Locale

// Controles del audio: play/reanudar, pausa, stop, progreso y volumen.
// Cada botón solo se habilita cuando tiene sentido según el estado actual.
@Composable
fun ControlesAudio(
    estado: EstadoAudio,
    posicionMs: Int,
    duracionMs: Int,
    volumen: Float,
    onPlay: () -> Unit,
    onPausa: () -> Unit,
    onStop: () -> Unit,
    onVolumen: (Float) -> Unit
) {
    val textoEstado = when (estado) {
        EstadoAudio.DETENIDO -> stringResource(R.string.estado_detenido)
        EstadoAudio.REPRODUCIENDO -> stringResource(R.string.estado_reproduciendo)
        EstadoAudio.PAUSADO -> stringResource(R.string.estado_pausado)
    }
    val textoPlay = if (estado == EstadoAudio.PAUSADO) {
        stringResource(R.string.btn_reanudar)
    } else {
        stringResource(R.string.btn_play)
    }
    val progreso = if (duracionMs > 0) posicionMs.toFloat() / duracionMs else 0f

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.titulo_audio),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.etiqueta_estado, textoEstado),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            LinearProgressIndicator(
                progress = { progreso },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.primaryContainer
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(formatearTiempo(posicionMs), style = MaterialTheme.typography.bodySmall)
                Text(formatearTiempo(duracionMs), style = MaterialTheme.typography.bodySmall)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onPlay,
                    enabled = estado != EstadoAudio.REPRODUCIENDO,
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    modifier = Modifier.weight(1f)
                ) { Text(textoPlay, fontSize = 13.sp, maxLines = 1) }

                Button(
                    onClick = onPausa,
                    enabled = estado == EstadoAudio.REPRODUCIENDO,
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    modifier = Modifier.weight(1f)
                ) { Text(stringResource(R.string.btn_pausa), fontSize = 13.sp, maxLines = 1) }

                Button(
                    onClick = onStop,
                    enabled = estado != EstadoAudio.DETENIDO,
                    contentPadding = PaddingValues(horizontal = 4.dp),
                    modifier = Modifier.weight(1f)
                ) { Text(stringResource(R.string.btn_stop), fontSize = 13.sp, maxLines = 1) }
            }

            Text(
                text = stringResource(R.string.etiqueta_volumen),
                style = MaterialTheme.typography.bodyMedium
            )
            Slider(
                value = volumen,
                onValueChange = onVolumen,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.secondary,
                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                    inactiveTrackColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

private fun formatearTiempo(milisegundos: Int): String {
    val totalSegundos = milisegundos / 1000
    return String.format(Locale.ROOT, "%d:%02d", totalSegundos / 60, totalSegundos % 60)
}
