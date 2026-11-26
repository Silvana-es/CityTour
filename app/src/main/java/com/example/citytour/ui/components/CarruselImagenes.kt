package com.example.citytour.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.citytour.R
import com.example.citytour.data.Monumento

// Carrusel de imágenes (res/drawable) con botones y puntos indicadores
@Composable
fun CarruselImagenes(
    pagerState: PagerState,
    monumentos: List<Monumento>,
    onAnterior: () -> Unit,
    onSiguiente: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            shape = RoundedCornerShape(28.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth().height(260.dp)
            ) { pagina ->
                Image(
                    painter = painterResource(id = monumentos[pagina].imagen),
                    contentDescription = stringResource(
                        R.string.cd_imagen,
                        stringResource(monumentos[pagina].titulo)
                    ),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onAnterior,
                enabled = pagerState.currentPage > 0
            ) { Text(stringResource(R.string.btn_anterior)) }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(monumentos.size) { indice ->
                    val activo = indice == pagerState.currentPage
                    Spacer(
                        modifier = Modifier
                            .size(if (activo) 12.dp else 8.dp)
                            .clip(CircleShape)
                            .background(
                                if (activo) MaterialTheme.colorScheme.secondary
                                else MaterialTheme.colorScheme.primary
                            )
                    )
                }
            }

            TextButton(
                onClick = onSiguiente,
                enabled = pagerState.currentPage < monumentos.size - 1
            ) { Text(stringResource(R.string.btn_siguiente)) }
        }

        Text(
            text = stringResource(
                R.string.contador_imagen,
                pagerState.currentPage + 1,
                monumentos.size
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
