package com.suhani.quizzify.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.suhani.quizzify.R
import com.suhani.quizzify.models.data

class ScoreAdapter(val context: Context?, val list:List<data>, var i: Int =1):RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {


    inner class ScoreViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var textviewName: TextView = itemView.findViewById(R.id.username)
        var imageviewUser: ImageView = itemView.findViewById(R.id.userImg)
        var textviewScore: TextView = itemView.findViewById(R.id.userscore)
        var textviewRank:TextView=itemView.findViewById(R.id.userrank)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.leaderboard_item,parent,false)
        return ScoreViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        holder.textviewName.text=list[position].name
        /*if (context != null) {
            Glide.with(context).load(list[position].img).into(holder.imageviewUser)
        }*/
        holder.textviewRank.text=i.toString()
        i++
    }
}