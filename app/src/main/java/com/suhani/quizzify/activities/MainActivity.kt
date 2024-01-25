package com.suhani.quizzify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.suhani.quizzify.R
import com.suhani.quizzify.databinding.ActivityMainBinding
import com.suhani.quizzify.fragments.AddquizFragment
import com.suhani.quizzify.fragments.home_fragment
import com.suhani.quizzify.fragments.leaderboard_fragment
import com.suhani.quizzify.fragments.profile
import com.suhani.quizzify.models.data

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var binding:ActivityMainBinding
//    private lateinit var userid:String
//    private lateinit var dbRef: DatabaseReference
//    private lateinit var name:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
//        userid= auth.currentUser!!.uid
//        dbRef= FirebaseDatabase.getInstance().getReference("Users")
        replaceFragment(home_fragment())
//        name=intent.getStringExtra("name").toString()
        //saveData()
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.Home ->replaceFragment(home_fragment())
                R.id.Profile ->replaceFragment(profile())
                R.id.Leaderboard ->replaceFragment(leaderboard_fragment())
                R.id.addquiz->replaceFragment(AddquizFragment())

            else->{

            }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
//    private fun saveData() {
//        val data= data(name)
//        dbRef.child(userid).push().setValue(data)
//    }
}