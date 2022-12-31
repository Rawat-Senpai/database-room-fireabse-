package com.example.data_form

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val db= Firebase.firestore
    private lateinit var mAuth: FirebaseAuth
    lateinit var viewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        mAuth=Firebase.auth

        viewModel= ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

        main_show.setOnClickListener(){
            val intent=Intent(this,showData::class.java)
            startActivity(intent)
        }

        main_firebaseShow.setOnClickListener(){
            val intent= Intent(this,firebaseDataShow::class.java)
            startActivity(intent)
        }


        uploadBtn.setOnClickListener(){

            val name=userName.text.toString()
            val address=userAddress.text.toString()
            val number=userNumber.text.toString()

            val details= hashMapOf(
                "name" to name,
                "address" to address,
                "number" to number
            )
        if(name.isNotEmpty() and address.isNotEmpty() and number.isNotEmpty()) {


            if (checkForInternet(this)) {
                db.collection("user_details").document().set(details)
                    .addOnSuccessListener {
                        Toast.makeText(
                            this,
                            "Data uploaded on firebase successfully ",
                            Toast.LENGTH_SHORT
                        ).show()
                        userName.text.clear()
                        userNumber.text.clear()
                        userAddress.text.clear()
                    }
            } else {

                Toast.makeText(this, "data uploaded on local database ", Toast.LENGTH_SHORT)
                    .show()
                val add = Note(name, address, number)

                viewModel.addNote(add)
                userName.text.clear()
                userNumber.text.clear()
                userAddress.text.clear()
            }

        }else
        {
            Toast.makeText(this,"enter all details ",Toast.LENGTH_SHORT).show()
        }
        }


        /*
////            db.collection("user_details").document().set(details)
////                .addOnSuccessListener {
////                    Toast.makeText(this, "Data uploaded on firebase successfully ", Toast.LENGTH_SHORT).show()
////                    userName.text.clear()
////                    userNumber.text.clear()
////                    userAddress.text.clear()
////                }.addOnCanceledListener {
////                    Toast.makeText(this,"data uploaded on local database ",Toast.LENGTH_SHORT).show()
////                    val add=Note(name,address,number)
////
////                    viewModel.addNote(add)
////                    userName.text.clear()
////                    userNumber.text.clear()
////                    userAddress.text.clear()
////                }
//
//
//
//
//
//
//
//        }
//
        */



    }

    private fun checkForInternet(context: Context): Boolean {
        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

}