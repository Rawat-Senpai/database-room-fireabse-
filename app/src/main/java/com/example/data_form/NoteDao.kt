package com.example.data_form

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.w3c.dom.Node

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
     fun insert(note:Note)

    @Delete
     fun delete(note:Note)

    @Query("Select * from notetable order by id ASC")
    fun getALlNotes(): LiveData<List<Note>>

    @Query("Select * from notetable LIMIT 1")
    fun getFirst():Note



}