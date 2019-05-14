package fi.jamk.launchmap

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun launchActivity(v: View){
        // explicit intent
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }

    fun launchMap(v: View){
        // get latitude and longitude values
        val lat = latEditText.text.toString().toDouble()
        val lng = lngEditText.text.toString().toDouble()
        // Build the intent
        val location = Uri.parse("geo:$lat,$lng")
        val mapIntent = Intent(Intent.ACTION_VIEW, location)

        // Verify it resolves
        val activities: List<ResolveInfo> = packageManager.queryIntentActivities(mapIntent, 0)
        val isIntentSafe: Boolean = activities.isNotEmpty()

        // Start an activity if it's safe
        if (isIntentSafe) {
            startActivity(mapIntent)
        } else {
            Toast.makeText(this, "There is no activity to handle map intent!", Toast.LENGTH_LONG).show();
        }
    }
}
