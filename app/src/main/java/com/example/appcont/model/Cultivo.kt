package com.example.appcont.model

import lombok.*

@Data
@NoArgsConstructor
@AllArgsConstructor
class Cultivo() {
    var idCultivo: String = ""
    var nombre: String = ""
    var sociedad: Boolean = false
}
