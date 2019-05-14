package fi.jamk.recyclerviewexample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.employee_item.view.*
import org.json.JSONArray
import org.json.JSONObject

// Employees Adapter, used in RecyclerView in MainActivity
class EmployeesAdapter(private val employees: JSONArray) : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {

    // create UI View Holder from XML layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeesAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)         // kyky ladata xml-tiedostoja
        val view = layoutInflater.inflate(R.layout.employee_item, parent, false)
        return ViewHolder(view)  //
    }

    // return item count in employees
    override fun getItemCount(): Int = employees.length()  // kuinka paljon tavaraa??

    // bind data to UI View Holder
    override fun onBindViewHolder(holder: EmployeesAdapter.ViewHolder, position: Int) { // position tärkeä!!
        // employee to bind UI
        val employee: JSONObject = employees.getJSONObject(position)
        // name
        holder.nameTextView.text = employee["lastName"].toString() + " " + employee["firstName"].toString()
        /*
        holder.nameTextView.text = holder.nameTextView.context.getString(
            R.string.fullname_text,
            employee["lastName"].toString(),
            employee["firstName"].toString())
            */
    }

    // View Holder class to hold UI views
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.nameTextView
    }

}
