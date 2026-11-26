package com.example.citytour.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.citytour.R
import com.example.citytour.audio.EstadoAudio
import com.example.citytour.audio.ReproductorAudio
import com.example.citytour.data.listaMonumentos
import com.example.citytour.ui.components.CarruselImagenes
import com.example.citytour.ui.components.ControlesAudio
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MonumentoScreen(reproductor: ReproductorAudio) {
    val monumentos = listaMonumentos
    val pagerState = rememberPagerState(pageCount = { monumentos.size })
    val scope = rememberCoroutineScope()
    val monumentoActual = monumentos[pagerState.currentPage]

    // Evento: al cambiar de imagen se carga el audio del nuevo monumento
    // (el anterior se libera y el nuevo queda detenido, listo para reproducirse)
    LaunchedEffect(pagerState.currentPage) {
        reproductor.cargar(monumentos[pagerState.currentPage].audio)
    }

    // Mientras el audio se reproduce, se actualiza la barra de progreso cada 250 ms
    LaunchedEffect(reproductor.estado) {
        reproductor.sincronizarPosicion()
        while (reproductor.estado == EstadoAudio.REPRODUCIENDO) {
            delay(250)
            reproductor.sincronizarPosicion()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.titulo_app),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(
            text = stringResource(R.string.subtitulo_app),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))

        CarruselImagenes(
            pagerState = pagerState,
            monumentos = monumentos,
            onAnterior = {
                scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
            },
            onSiguiente = {
                scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(monumentoActual.titulo),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(monumentoActual.descripcion),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        ControlesAudio(
            estado = reproductor.estado,
            posicionMs = reproductor.posicionMs,
            duracionMs = reproductor.duracionMs,
            volumen = reproductor.volumen,
            onPlay = { reproductor.reproducir() },
            onPausa = { reproductor.pausar() },
            onStop = { reproductor.detener() },
            onVolumen = { reproductor.cambiarVolumen(it) }
        )
    }
}
