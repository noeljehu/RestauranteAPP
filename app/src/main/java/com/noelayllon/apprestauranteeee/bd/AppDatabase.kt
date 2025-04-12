package com.noelayllon.apprestauranteeee.bd

import androidx.room.Database
import androidx.room.TypeConverters
import com.noelayllon.apprestauranteeee.Repository.ClienteDao
import com.noelayllon.apprestauranteeee.Repository.ProductoDao
import com.noelayllon.apprestauranteeee.modelo.Cliente
import com.noelayllon.apprestauranteeee.modelo.DetallePedido
import com.noelayllon.apprestauranteeee.modelo.Pedido
import com.noelayllon.apprestauranteeee.modelo.Producto
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.noelayllon.apprestauranteeee.Repository.DetallePedidoDao
import com.noelayllon.apprestauranteeee.Repository.PedidoDao

// AppDatabase.kt
@Database(
    entities = [
        Cliente::class,
        Producto::class,
        Pedido::class,
        DetallePedido::class
    ],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {  // Eliminar la implementación de Appendable
    abstract fun clienteDao(): ClienteDao
    abstract fun productoDao(): ProductoDao
    abstract fun pedidoDao(): PedidoDao
    abstract fun detallePedidoDao(): DetallePedidoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "restaurante_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
