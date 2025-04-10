package com.noelayllon.apprestaurante.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.noelayllon.apprestaurante.Repository.RestauranteDao
import com.noelayllon.apprestaurante.modelo.Cliente
import com.noelayllon.apprestaurante.modelo.DetallePedido
import com.noelayllon.apprestaurante.modelo.Pedido
import com.noelayllon.apprestaurante.modelo.Producto
import com.noelayllon.apprestaurante.modelo.Trabajador



@Database(
    entities = [
        Cliente::class,
        Trabajador::class,
        Producto::class,
        Pedido::class,
        DetallePedido::class
    ],
    version = 1,
    exportSchema = false
)
abstract class RestauranteDatabase : RoomDatabase() {

    // DAOs
    abstract fun restauranteDao(): RestauranteDao

    // Singleton para la base de datos
    companion object {
        @Volatile
        private var INSTANCE: RestauranteDatabase? = null

        fun getInstance(context: Context): RestauranteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestauranteDatabase::class.java,
                    "restaurante_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}