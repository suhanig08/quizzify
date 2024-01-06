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
        binding=ActivityLoginintroBinding.inflate(layoutInflater)
        val auth:FirebaseAuth=FirebaseAuth.getInstance()
        setContentView(binding.root)
        if(auth.currentUser!=null) {
            Toast.makeText(this, "Already logged in", Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }
        binding.btngetStarted.setOnClickListener {
            redirect("LOGIN")
        }
    }
    private fun redirect(name:String){
        val intent=when(name){
            "LOGIN"->Intent(this, SignInActivity::class.java)
            "MAIN"->Intent(this, MainActivity::class.java)
            else->throw Exception("No path exists")
        }
        startActivity(intent)
        finish()
    }
}