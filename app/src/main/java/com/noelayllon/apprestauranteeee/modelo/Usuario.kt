package com.noelayllon.apprestauranteeee.modelo

data class Usuario(
    val nombre: String,
    val telefono: String,
    val puesto: String,
    val fechaContratacion: com.google.firebase.Timestamp,
    val salario: String
)
