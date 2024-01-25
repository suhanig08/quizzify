package com.suhani.quizzify.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.suhani.quizzify.R
import com.suhani.quizzify.activities.SignInActivity
import com.suhani.quizzify.activities.profileActivity
import com.suhani.quizzify.databinding.FragmentProfileBinding
import com.suhani.quizzify.models.Quizmodel
import com.suhani.quizzify.models.data
import com.suhani.quizzify.models.quizData


class profile : Fragment() {


    private lateinit var binding: FragmentProfileBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var auth:FirebaseAuth
    private lateinit var userid:String
    private var score:Long=0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth=FirebaseAuth.getInstance()
        userid= auth.currentUser!!.uid
        dbRef= FirebaseDatabase.getInstance().getReference("Users").child(userid)
        setUpFireBase()
        view.findViewById<TextView>(R.id.total_score).text=score.toString()
        binding.logoutBtn.setOnClickListener{
            val intent= Intent(context, SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.profileBtn.setOnClickListener{
            val intent= Intent(context, profileActivity::class.java)
            intent.putExtra("userid","")
            startActivity(intent)
        }
      }

    private fun setUpFireBase() {
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (quizSnap in snapshot.children) {
                        val quizData=quizSnap.getValue(quizData::class.java)
                         score+= quizData!!.score
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    }
