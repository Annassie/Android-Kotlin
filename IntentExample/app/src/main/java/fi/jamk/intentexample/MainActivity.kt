package fi.jamk.intentexample

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

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
        // Build the intent
        val location = Uri.parse("geo:0,0?q=Piippukatu 2, Jyväskylä, Finland")
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

    fun launchWebAddress(v: View){
        // Build the intent
        val web = Uri.parse("http://jamk.fi")
        val webIntent = Intent(Intent.ACTION_VIEW, web)

        // Always use string resources for UI text.

        val title = resources.getString(R.string.choose_text)
        // Create intent to show chooser
        val chooser = Intent.createChooser(webIntent, title)

        // Verify the intent will resolve to at least one activity
        if (webIntent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        }
    }
}
