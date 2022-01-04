package com.example.mvvm

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Parcel::class], version = 1)
abstract class ParcelDatabase : RoomDatabase() {
    abstract val parcelDAO: ParcelDao

    companion object {
        @Volatile
        private var INSTANCE: ParcelDatabase? = null
        fun getInstance(context: Context): ParcelDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ParcelDatabase::class.java,
                        "parcel_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}