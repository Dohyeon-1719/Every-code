package kr.ac.kumoh.s20170419.everydaymath

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class SampleAdapter(
    private val dataSet: List<Int>
) : RecyclerView.Adapter<SampleAdapter.NumberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val layoutView: LinearLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.analysis_item, parent, false) as LinearLayout

        return NumberViewHolder(layoutView)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        if(position < dataSet.size) {
            holder.number.text = dataSet[position].toString()
        }
    }

    inner class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val number = itemView.findViewById(R.id.number) as TextView
    }
}