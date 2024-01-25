package com.suhani.quizzify.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.suhani.quizzify.databinding.ActivityStartTestBinding
import com.suhani.quizzify.models.Quizmodel


class startQuiz : AppCompatActivity() {

    private lateinit var binding:ActivityStartTestBinding
    private lateinit var totaltime:TextView
    private lateinit var startquiz:Button
    private lateinit var backB:ImageView
    private var score=0
    private lateinit var x:String
    private var position=0
    private lateinit var dbRef: DatabaseReference
    private var quizlist = mutableListOf<Quizmodel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityStartTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        x = intent.getStringExtra("GENRE").toString()
        position=intent.getIntExtra("position",0)
        score=intent.getIntExtra("score",0)
        binding.lastscore.text=score.toString()
        dbRef = FirebaseDatabase.getInstance().getReference("quizzes").child(x)
        setUpFireBase()
    }

    private fun setUpFireBase() {

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                quizlist.clear()
                if (snapshot.exists()) {
                    for (quizSnap in snapshot.children) {
                        val quizData=quizSnap.getValue(Quizmodel::class.java)
                        quizlist.add(quizData!!)
                        Log.e("runnn",quizlist.toString())
                    }
                    initializeit()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    @SuppressLint("SuspiciousIndentation")
    private fun initializeit() {
        binding.category.text=x
        totaltime=binding.time
        totaltime.text="${quizlist[position].time} s"
        startquiz=binding.startquizbtn
        backB=binding.startquizbackbtn
        binding.questionnumber.text= quizlist[position].amt.toString()


        backB.setOnClickListener{
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        startquiz.setOnClickListener {
            val intent= Intent(this,QuestionActivity::class.java)
            intent.putExtra("title",x)
            intent.putExtra("time",quizlist[position].time.toString())
            startActivity(intent)
        }
    }

}
