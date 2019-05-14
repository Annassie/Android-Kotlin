package fi.jamk.anadaptiveui

import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.google.android.flexbox.FlexboxLayout
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    companion object{
        private const val SELECTED_COUNTRY_INDEX = "selectedCountryIndex"
    }

    private val countries = ArrayList<Country>()
    private lateinit var countryAdapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()

        if (savedInstanceState != null) {
            val index = savedInstanceState.getInt(SELECTED_COUNTRY_INDEX)
            if (index >= 0 && index < countries.size) {
                countryAdapter.selectedCountryIndex = index
                loadFlag(countries[index].flag)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SELECTED_COUNTRY_INDEX, countryAdapter.selectedCountryIndex)
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<View>(R.id.list) as RecyclerView
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = layoutManager

        loadData()

        countryAdapter = CountryAdapter(this, countries, object : CountryAdapter.OnItemClickListener {
            override fun onItemClick(location: Country) {
                loadFlag(location.flag)
            }
        })
        recyclerView.adapter = countryAdapter
    }

    private fun loadData() {
        val json: String? = loadJsonString()
        val array: JSONArray? = loadJsonArray(json)
        loadLocations(array)
    }

    private fun loadJsonString(): String? {
        var json: String? = null
        try {
            val inputStream = assets.open("data.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            Log.e("MainActivity", e.toString())
        }
        return json
    }

    private fun loadJsonArray(json: String?): JSONArray? {
        var array: JSONArray? = null
        try {
            array = JSONArray(json)
        } catch (e: JSONException) {
            Log.e("MainActivity", e.toString())
        }
        return array
    }

    private fun loadLocations(array: JSONArray?) {
        if (array != null) {
            for (i in 0 until array.length()) {
                try {
                    val jsonObject = array[i] as JSONObject
                    val stringArray = jsonObject["flag"] as JSONArray
                    val flag = (0 until stringArray.length()).mapTo(ArrayList<String>()) { stringArray.getString(it) }
                    val location = Country(jsonObject["name"] as String, flag)
                    countries.add(location)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun mapWeatherToDrawable(flag: String): Drawable? {
        var drawableId = 0
        when (flag) {
            "european-union" -> drawableId = R.drawable.ic_european_union
            "argentina" -> drawableId = R.drawable.ic_argentina
            "france" -> drawableId = R.drawable.ic_france
            "macao" -> drawableId = R.drawable.ic_macao
            "saudi-arabia" -> drawableId = R.drawable.ic_saudi_arabia
            "south-africa" -> drawableId = R.drawable.ic_south_africa
        }
        return ContextCompat.getDrawable(this, drawableId)
    }

    private fun loadFlag(flag: List<String>) {
        val forecastView = findViewById<View>(R.id.flag) as FlexboxLayout
        for (i in 0 until forecastView.childCount) {
            val dayView = forecastView.getChildAt(i) as AppCompatImageView
            dayView.setImageDrawable(mapWeatherToDrawable(flag[i]))
        }
    }
}