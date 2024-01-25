package com.suhani.quizzify.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.suhani.quizzify.activities.SignInActivity
import com.suhani.quizzify.activities.profileActivity
import com.suhani.quizzify.databinding.FragmentProfileBinding


class profile : Fragment() {


    private lateinit var binding: FragmentProfileBinding

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
    }
