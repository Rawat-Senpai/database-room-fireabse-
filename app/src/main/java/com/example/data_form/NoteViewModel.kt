package com.example.data_form

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch

class NoteViewModel(application: Application):AndroidViewModel(application) {

    val allNote: LiveData<List<Note>>

    val repository: NoteRepository

    init{
        val dao= NoteDataBase.getDatabase(application).getNoteDao()
        repository= NoteRepository(dao)
        allNote=repository.allNote
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(note)
    }

    fun addNote(note: Note)= viewModelScope.launch (Dispatchers.IO){
        repository.insert(note)
    }

}