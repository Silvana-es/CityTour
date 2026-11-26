package com.example.citytour.data

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.example.citytour.R

// Cada monumento une su imagen (res/drawable) con su audio (res/raw)
data class Monumento(
    @StringRes val titulo: Int,
    @StringRes val descripcion: Int,
    @DrawableRes val imagen: Int,
    @RawRes val audio: Int
)

val listaMonumentos = listOf(
    Monumento(
        titulo = R.string.monumento_1_titulo,
        descripcion = R.string.monumento_1_desc,
        imagen = R.drawable.monumento_1,
        audio = R.raw.audio_monumento_1
    ),
    Monumento(
        titulo = R.string.monumento_2_titulo,
        descripcion = R.string.monumento_2_desc,
        imagen = R.drawable.monumento_2,
        audio = R.raw.audio_monumento_2
    ),
    Monumento(
        titulo = R.string.monumento_3_titulo,
        descripcion = R.string.monumento_3_desc,
        imagen = R.drawable.monumento_3,
        audio = R.raw.audio_monumento_3
    )
)
