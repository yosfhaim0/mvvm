package com.example.mvvm

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvm.Parcel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.selects.select

@Dao
interface ParcelDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertParcel(parcel: Parcel):Long

    @Delete
    suspend fun deleteParcel(parcel: Parcel):Int

    @Update
    suspend fun updateParcel(parcel:Parcel):Int

    @Query("DELETE FROM parcel_data_table")
    suspend fun deleteAll() : Int


    @Query("SELECT * FROM parcel_data_table ORDER BY parcel_id ASC")
    fun readAllData():Flow<List<Parcel>>
}