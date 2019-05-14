package fi.jamk.imagetest

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonClicked(v: View) {
        //GetImageAsyncTask(this).execute("http://ptm.fi/data/android2.png")
        Glide.with(this).load("http://ptm.fi/data/android3.jpg").into(imageView);
    }

    // inner class, load image with AsyncTask
    private class GetImageAsyncTask internal constructor(context:MainActivity): AsyncTask<String, Void, Bitmap?>() {

        // weak reference to the activity
        private val activityReference: WeakReference<MainActivity> = WeakReference(context)

        // AsyncTask own thread
        override fun doInBackground(vararg urls: String?): Bitmap? {
            return try {
                // create a connection
                val connection = URL(urls[0]).openConnection() as HttpURLConnection
                // load bitmap from above connection
                BitmapFactory.decodeStream(connection.inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        // onPostExecute will be called after doInBackground is finished - UI can be modified
        override fun onPostExecute(bitmap: Bitmap?) {
            super.onPostExecute(bitmap)

            // get a reference to the activity if it is still there
            val activity = activityReference.get()
            // if activity is destroyed or are finishing, just return (image cannot be displayed)
            if (activity == null || activity.isFinishing) return
            // Activity is still active, we can show the loaded bitmap
            if (bitmap != null) {
                activity.imageView.setImageBitmap(bitmap)
                //Toast.makeText(activity.context,"Image loaded from the URL with AsyncTask", Toast.LENGTH_LONG).show()
            }
        }
    }




} // MainActivity

