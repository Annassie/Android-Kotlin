package fi.jamk.fragmentexample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openFragmentOne(v: View){
        var fragmentOne = FragmentOne()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragmentOne)
            .commit()
    }

    fun openFragmentTwo(v: View){
        var fragmentTwo = FragmentTwo()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragmentTwo)
            .commit()
        
    }
}
