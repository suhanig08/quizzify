package com.suhani.quizzify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.suhani.quizzify.R
import com.suhani.quizzify.models.Quizmodel
import com.suhani.quizzify.models.data

class editProfileActivity : AppCompatActivity() {

    private lateinit var dbRef:DatabaseReference
    private lateinit var name:EditText
    private lateinit var userid:String
    private lateinit var auth:FirebaseAuth
    var score=0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        auth=FirebaseAuth.getInstance()
        userid= auth.currentUser!!.uid
        dbRef=FirebaseDatabase.getInstance().getReference("Users").child(userid)
        name=findViewById(R.id.Namebox)
        //setUpFireBase()
        findViewById<Button>(R.id.savebtn).setOnClickListener {
            saveaddquizdata()
        }
    }



    private fun saveaddquizdata() {
        val etname=name.text.toString()

        if (etname.isEmpty()) {
            name.error = "Please enter the name"
        } else {
            val data =
                data(
                    etname
                )
            dbRef.push().setValue(data)
                .addOnCompleteListener {
                    Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,profileActivity::class.java)
                    intent.putExtra("userid",userid)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}