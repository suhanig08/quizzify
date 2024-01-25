package com.suhani.quizzify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.suhani.quizzify.databinding.ActivityLoginintroBinding

class loginintro : AppCompatActivity() {
    private lateinit var binding: ActivityLoginintroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginintroBinding.inflate(layoutInflater)
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        setContentView(binding.root)
        if (auth.currentUser != null) {
            Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btngetStarted.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            //intent.putExtra("name", "")
            startActivity(intent)
            finish()
        }
    }
}