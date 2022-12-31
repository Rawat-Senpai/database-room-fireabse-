package com.example.data_form

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private  val userList:ArrayList<User>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.fireabase_item,parent,false)

        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.fname.text=userList[position].name
        holder.fnumber.text=userList[position].number
        holder.fadderss.text=userList[position].address

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val fname:TextView=itemView.findViewById(R.id.pName)
        val fadderss:TextView=itemView.findViewById(R.id.pAdderess)
        val fnumber:TextView=itemView.findViewById(R.id.pNumber)

    }
}