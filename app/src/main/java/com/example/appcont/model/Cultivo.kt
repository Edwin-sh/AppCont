package com.example.appcont.model

import lombok.*

@Data
@NoArgsConstructor
@AllArgsConstructor
class Cultivo(
    val idCultivo: String,
    val nombre: String,
    val sociedad: Boolean
)
