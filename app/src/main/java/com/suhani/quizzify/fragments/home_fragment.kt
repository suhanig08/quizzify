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
import com.suhani.quizzify.adapters.QuizAdapter


class home_fragment : Fragment() {

    private lateinit var adapter: QuizAdapter
    private var titlelist= mutableListOf<String>()
   // private lateinit var firestore: FirebaseFirestore
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setUpFireStore()
        setUpFireBase()
        adapter = QuizAdapter(context,titlelist)
        val recyclerView: RecyclerView = view.findViewById(R.id.quizRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

    }

    private fun setUpFireBase() {
        dbRef = FirebaseDatabase.getInstance().getReference("quizzes")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //quizlist.clear()
                if (snapshot.exists()) {
                    for (quizSnap in snapshot.children) {
                        val title=quizSnap.key.toString()
                        titlelist.add(title)
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
    }


    /*private fun setUpFireStore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference: CollectionReference = firestore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            quizlist.clear()
            quizlist.addAll(value.toObjects(Quizmodel::class.java))
            adapter.notifyDataSetChanged()
        }
    }*/
}
