package com.example.data_form

import android.content.Intent
import android.media.metrics.Event
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_firebase_data_show.*
import org.w3c.dom.Document

class firebaseDataShow : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList:ArrayList<User>
    private lateinit var myAdapter: MyAdapter
    private var db=Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_data_show)


        Toast.makeText(this, "wait a moment ", Toast.LENGTH_SHORT).show()
        
        recyclerView=findViewById(R.id.fireRV)
        rv_main.setOnClickListener()
        {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList= arrayListOf()

        myAdapter= MyAdapter(userArrayList)

        EventChangeListner()


    }

    private fun EventChangeListner(){
        /*
        db=FirebaseFirestore.getInstance()
        db.collection("user_details").addSnapshotListener(object : EventListener<QuerySnapshot>{

            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error!=null)
                {
                   Log.e("Firebase Error",error.message.toString())
                    return
                }

                for(dc:DocumentChange in value?.documentChanges!!){
                    if(dc.type==DocumentChange.Type.ADDED)
                    {
                        userArrayList.add(dc.document.toObject(User::class.java))
                    }
                }

                myAdapter.notifyDataSetChanged()

            }

        })
*/

        db.collection("user_details").get()
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val user:User?= data.toObject(User::class.java)
                        if(user!=null)
                        {
                            userArrayList.add(user)
                        }
                    }

                    recyclerView.adapter=MyAdapter(userArrayList)

                }
            }
            .addOnFailureListener {
                Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
            }


    }
}