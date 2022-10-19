package org.wit.mygolftracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.wit.mygolftracker.databinding.CardGolfRoundBinding
import org.wit.mygolftracker.models.GolfRoundModel

interface GolfTrackerListener {
    fun onGolfRoundClick(golfRound: GolfRoundModel)
}

class GolfTrackerAdapter constructor(private var golfRounds: List<GolfRoundModel>,
                                   private val listener: GolfTrackerListener) :
    RecyclerView.Adapter<GolfTrackerAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardGolfRoundBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val golfRound = golfRounds[holder.adapterPosition]
        holder.bind(golfRound, listener)
    }

    override fun getItemCount(): Int = golfRounds.size

    class MainHolder(private val binding : CardGolfRoundBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(golfRound: GolfRoundModel, listener: GolfTrackerListener) {
            binding.roundCourse.text = golfRound.course
            binding.roundDate.text = golfRound.date
            binding.root.setOnClickListener { listener.onGolfRoundClick(golfRound) }
        }
    }
}