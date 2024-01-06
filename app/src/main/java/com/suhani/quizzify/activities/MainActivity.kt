package com.suhani.quizzify.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.suhani.quizzify.R
import com.suhani.quizzify.databinding.ActivityMainBinding
import com.suhani.quizzify.home_fragment
import com.suhani.quizzify.leaderboard_fragment
import com.suhani.quizzify.profile

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(home_fragment())
        auth=FirebaseAuth.getInstance()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.Home ->replaceFragment(home_fragment())
                R.id.Profile ->replaceFragment(profile())
                R.id.Leaderboard ->replaceFragment(leaderboard_fragment())

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
}