package com.suhani.quizzify.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.suhani.quizzify.R
import com.suhani.quizzify.fragments.home_fragment
import com.suhani.quizzify.models.Question
import com.suhani.quizzify.models.Quiz
import com.suhani.quizzify.models.Quizmodel
import com.suhani.quizzify.models.data
import com.suhani.quizzify.models.quizData
import java.io.Serializable

class ResultActivity : AppCompatActivity() {

    private lateinit var dbRef:DatabaseReference
    //private lateinit var dbRef2:DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var userid:String
    private lateinit var x:String
    private var questions =mutableListOf<Quizmodel>()
    private var users =mutableListOf<quizData>()
    var score=0L

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        auth=FirebaseAuth.getInstance()
        userid= auth.currentUser!!.uid
        x=intent.getStringExtra("title").toString()
        //dbRef2 = FirebaseDatabase.getInstance().getReference("Users").child(userid)
        questions=intent.getSerializableExtra("list") as MutableList<Quizmodel>
        Log.d("listttt",questions.toString())
        setUpFireBase()
        setAnswerView()

        //setUpFireBaseUser()

        findViewById<ImageView>(R.id.backtostart).setOnClickListener{
            val intent=Intent(this,home_fragment::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun setUpFireBase() {
        dbRef = FirebaseDatabase.getInstance().getReference("quizzes").child(x)
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (quizSnap in snapshot.children) {
                        val quizData=quizSnap.getValue(Quizmodel::class.java)
                        questions.add(quizData!!)
                    }
                    calculateScore()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun setAnswerView() {
       val builder=StringBuilder("")
        for(entry in questions){
            builder.append("<font color='#18206F'><b>Questions: ${entry.description}</b></font><br/><br/>")
            builder.append("<font color='#009688'>Answer: ${entry.answer}</font><br/><br/>")
        }
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.N){
            findViewById<TextView>(R.id.txtAnswer).text= Html.fromHtml(builder.toString(),Html.FROM_HTML_MODE_COMPACT);
        }else{
            findViewById<TextView>(R.id.txtAnswer).text=Html.fromHtml(builder.toString());
        }
    }

    private fun calculateScore() {
        for (entry in questions){
            if(entry.answer==entry.userans) {
                score += 10
            }
        }

        findViewById<TextView>(R.id.txtScore).text="Your Score: $score"
    }

//    private fun setUpFireBaseUser() {
//        dbRef2.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.e("checkin",snapshot.toString())
//                if (snapshot.exists()) {
//                    for (userSnap in snapshot.children) {
//                        val userData=userSnap.getValue(quizData::class.java)
//                        users.add(userData!!)
//                        Log.d("userdata",users.toString())
//                    }
//                    saveaddquizdata()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//    }

//    private fun saveaddquizdata() {
//
//            val data =
//                quizData(
//                    score
//                )
//            Log.d("scoree",data.score.toString())
//            dbRef2.child(x).push().setValue(data)
//                .addOnCompleteListener {
//                    Toast.makeText(this,"Score Saved", Toast.LENGTH_SHORT).show()
//                }.addOnFailureListener { err ->
//                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
//                }
//        }
    }
