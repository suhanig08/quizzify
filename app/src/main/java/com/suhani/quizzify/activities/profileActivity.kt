package com.suhani.quizzify.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.suhani.quizzify.R
import com.suhani.quizzify.models.Quizmodel
import com.suhani.quizzify.models.user

class profileActivity : AppCompatActivity() {

    private lateinit var dbRef:DatabaseReference
    private lateinit var userid:String
    private var userlist = mutableListOf<user>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userid=intent.getStringExtra("userid").toString()
        dbRef=FirebaseDatabase.getInstance().getReference("Users").child(userid)

        if(userid!=""){
            dbRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnap in snapshot.children) {
                            val userData=userSnap.getValue(user::class.java)
                            userlist.add(userData!!)
                        }
                        findViewById<TextView>(R.id.profileName).text=userlist.na
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }


        findViewById<AppCompatButton>(R.id.personalInfo).setOnClickListener{
            val intent= Intent(this, editProfileActivity::class.java)
            startActivity(intent)
        }

        findViewById<AppCompatButton>(R.id.backB).setOnClickListener{
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}