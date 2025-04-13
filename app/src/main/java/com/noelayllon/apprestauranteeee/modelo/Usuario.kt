package com.noelayllon.apprestauranteeee.modelo

import com.google.firebase.Timestamp

data class Usuario(

    val nombre: String = "", // Valor por defecto para evitar problemas al deserializar
    val telefono: String = "",
    val puesto: String = "",
    val fechaContratacion: Timestamp = Timestamp.now(), // Valor por defecto
    val salario: String = ""
)