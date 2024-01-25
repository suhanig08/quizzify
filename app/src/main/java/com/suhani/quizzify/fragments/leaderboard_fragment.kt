package com.suhani.quizzify.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.suhani.quizzify.R
import com.suhani.quizzify.adapters.ScoreAdapter
import com.suhani.quizzify.models.user

class leaderboard_fragment : Fragment() {

    private lateinit var adapter: ScoreAdapter
    private lateinit var dbRef:DatabaseReference
    private  var list= mutableListOf<user>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leaderboard_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setUpFireBase()
        adapter = ScoreAdapter(context,list)
        val recyclerView: RecyclerView = view.findViewById(R.id.leaderBoardRecycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter


    }

    /*private fun setUpFireBase() {
        dbRef = FirebaseDatabase.getInstance().getReference("Users").child()

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //quizlist.clear()
                if (snapshot.exists()) {
                    for (quizSnap in snapshot.children) {
                        val title=quizSnap.key.toString()
                        list.add(title)
                        //Log.d("runCheck",titlelist.toString())
                        /*val quizData = quizSnap.getValue(Quizmodel::class.java)
                        quizlist.add(quizData!!)*/
                        Log.d("runCheck", titlelist.toString())
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }*/


    }
