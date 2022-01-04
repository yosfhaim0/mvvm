package com.example.mvvm

import androidx.lifecycle.LiveData

class ParcelRepository(private val dao: ParcelDao) {

    val parcels =dao.readAllData()

    suspend fun insert(parcel: Parcel):Long {
        return dao.insertParcel(parcel)
    }
    suspend fun update(parcel: Parcel): Int {
        return dao.updateParcel(parcel)
    }

    suspend fun delete(parcel: Parcel): Int {
        return dao.deleteParcel(parcel)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}
