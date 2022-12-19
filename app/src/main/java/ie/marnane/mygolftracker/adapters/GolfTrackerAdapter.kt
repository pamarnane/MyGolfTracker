package ie.marnane.mygolftracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.marnane.mygolftracker.databinding.CardGolfRoundBinding
import ie.marnane.mygolftracker.models.GolfRoundModel

interface GolfTrackerListener {
    fun onGolfRoundClick(golfRound: GolfRoundModel)
}

class GolfTrackerAdapter constructor(private var golfRounds: ArrayList<GolfRoundModel>,
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

    fun removeAt(position: Int) {
        golfRounds.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount(): Int = golfRounds.size

    class MainHolder(private val binding : CardGolfRoundBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(golfRound: GolfRoundModel, listener: GolfTrackerListener) {
            binding.root.tag = golfRound
            var totalScore = 0;
            binding.roundCourse.text = golfRound.course
            binding.roundDate.text = golfRound.date

            golfRound.scores.forEach {score ->
                totalScore = score.value
            }

            binding.totalScore.text = totalScore.toString();
            binding.root.setOnClickListener { listener.onGolfRoundClick(golfRound) }

        }
    }

}