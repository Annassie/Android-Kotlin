package fi.jamk.anadaptiveui

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CountryAdapter(private val context: Context, private val dataSet: List<Country>,
                      private val listener: OnItemClickListener)
    : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    private var selectedCountry: Country? = null

    var selectedCountryIndex: Int
        get() = dataSet.indexOf(selectedCountry)
        set(index) {
            this.selectedCountry = dataSet[index]
        }

    interface OnItemClickListener {
        fun onItemClick(location: Country)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var title: TextView = v.findViewById<View>(android.R.id.text1) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(android.R.layout.simple_selectable_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = dataSet[position].name
        holder.title.textSize = 22f
        holder.itemView.setOnClickListener {
            listener.onItemClick(dataSet[position])
            selectedCountry = dataSet[position]
            notifyDataSetChanged()
        }
        if (dataSet[position] == selectedCountry) {
            val backgroundColor = ContextCompat.getColor(context, R.color.color_primary_dark)
            holder.itemView.setBackgroundColor(backgroundColor)
            holder.title.setTextColor(Color.WHITE)
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
            holder.title.setTextColor(Color.BLACK)
        }
    }

    override fun getItemCount() = dataSet.size
}