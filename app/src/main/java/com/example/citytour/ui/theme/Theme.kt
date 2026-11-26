package com.example.citytour.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaRosaPastel = lightColorScheme(
    primary = RosaPastelIntenso,
    onPrimary = TextoOscuro,
    primaryContainer = RosaPastel,
    onPrimaryContainer = TextoOscuro,
    secondary = RosaAcento,
    onSecondary = Color.White,
    secondaryContainer = RosaSuperficie,
    onSecondaryContainer = TextoOscuro,
    background = RosaFondo,
    onBackground = TextoOscuro,
    surface = RosaFondo,
    onSurface = TextoOscuro,
    surfaceVariant = RosaSuperficie,
    onSurfaceVariant = TextoSuave,
    outline = RosaPastelIntenso
)

// Se usa un esquema fijo (sin color dinámico) para conservar siempre el diseño rosa pastel
@Composable
fun CityTourTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = EsquemaRosaPastel,
        typography = Typography,
        content = content
    )
}
