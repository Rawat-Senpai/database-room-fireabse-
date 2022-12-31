package com.example.data_form

import androidx.lifecycle.LiveData

class NoteRepository(private val noteDao: NoteDao) {

    val allNote: LiveData<List<Note>> = noteDao.getALlNotes()

//    val firstNote:Note=noteDao.getFirst()

    suspend fun insert(note: Note)
    {
        noteDao.insert(note)
    }

    suspend fun delete(note: Note){
        noteDao.delete(note)
    }



}