package com.example.data_form

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notetable")
class Note (@ColumnInfo(name ="name")val personName:String,
            @ColumnInfo(name="Address")val personAddress:String,
            @ColumnInfo(name="PhoneNumber")val personNumber:String,
@PrimaryKey(autoGenerate = true) var id:Int =0)