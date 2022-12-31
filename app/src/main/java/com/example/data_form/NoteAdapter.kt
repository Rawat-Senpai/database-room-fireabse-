package com.example.data_form

import android.R.id.button1
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.logging.Handler




class NoteAdapter(val context:Context,val noteClickInterface:NoteClickInterface ):RecyclerView.Adapter<NoteAdapter.ViewHolder>(){
//                val noteClickDeleteInterFace: NoteClicDeleteInterFace

    private val allNote=ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pName.setText(allNote.get(position).personName)
        holder.pAdd.setText(allNote.get(position).personAddress)
        holder.pNo.setText(allNote.get(position).personNumber)


//            noteClickDeleteInterFace.onDeleteIcon((allNote.get(position)))


    }

    override fun getItemCount(): Int {
        return allNote.size
    }
    fun updateList(newList: List<Note>){
        allNote.clear()
        allNote.addAll(newList)
        notifyDataSetChanged()

        if(allNote.size>0)
        {
            noteClickInterface.onNoteClick(allNote[0])
        }
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val pName=itemView.findViewById<TextView>(R.id.personName)
        val pAdd=itemView.findViewById<TextView>(R.id.personAdderess)
        val pNo=itemView.findViewById<TextView>(R.id.personNumber)
        val pImg=itemView.findViewById<ImageView>(R.id.uploadData)
    }

}

interface NoteClickInterface{
    fun onNoteClick(note:Note)
}

//private fun callNetworkConnection(){
//
//    checkInternetConnection = CheckInternetConnection(application)
//    checkInternetConnection.observe(this) { isConnected ->
//        if (isConnected) {
//
//        }
//
//
//    }
//}
