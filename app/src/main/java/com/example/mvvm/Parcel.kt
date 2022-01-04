package com.example.mvvm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "parcel_data_table")
data class Parcel
    (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "parcel_id")
    var pkg_id: Int,
    @ColumnInfo(name = "owner")
    var owner : String,
    @ColumnInfo(name = "owner_address")
    var owner_address : String
    )

