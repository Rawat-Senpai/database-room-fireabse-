package com.example.data_form

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class showData : AppCompatActivity() ,NoteClickInterface{

    lateinit var noteRV:RecyclerView
    lateinit var back:ImageView
    lateinit var viewModel: NoteViewModel
    private val db= Firebase.firestore
    private lateinit var mAuth: FirebaseAuth
    private lateinit var checkInternetConnection: CheckInternetConnection
    private lateinit var noteDao:NoteDao
    private lateinit var  noteAdapter:NoteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_data)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        callNetworkConnection()


        mAuth=Firebase.auth

        noteRV=findViewById(R.id.recyclerView)
        back=findViewById(R.id.backButton)
        noteRV.layoutManager= LinearLayoutManager(this)

         noteAdapter=NoteAdapter(this,this)


        noteRV.adapter=noteAdapter
        viewModel= ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        viewModel.allNote.observe(this, Observer { list->
            list?.let{
                //Toast.makeText(this,"automatic",Toast.LENGTH_SHORT).show()

               noteAdapter.updateList(it)

            }
        })

        back.setOnClickListener(){
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    /*
//    override fun onDeleteIcon(note: Note) {
//        viewModel.deleteNote(note)
//        Toast.makeText(this, "deleted ", Toast.LENGTH_SHORT).show()
//    }

     */

    override fun onNoteClick(note: Note) {

        if (checkForInternet(this)) {
            val name=note.personName
            val address=note.personAddress
            val number=note.personNumber

            val details= hashMapOf(
                "name" to name,
                "address" to address,
                "number" to number
            )
            db.collection("user_details").document().set(details)
                .addOnSuccessListener {
                    Toast.makeText(this,"Data uploaded on firebase successfully ",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.deleteNote(note)
                }

        } else {
            Toast.makeText(this, "Unable to upload on fireabase", Toast.LENGTH_SHORT).show()
        }


    }
    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            val network = connectivityManager.activeNetwork ?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                else -> false
            }
        } else {

            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun callNetworkConnection(){

        checkInternetConnection = CheckInternetConnection(application)
        checkInternetConnection.observe(this) { isConnected ->
            if (isConnected) {
                viewModel.allNote.observe(this, Observer { list->
                    list?.let{
                        //Toast.makeText(this,"automatic",Toast.LENGTH_SHORT).show()

                        noteAdapter.updateList(it)

                    }
                })
            }


        }
    }


}