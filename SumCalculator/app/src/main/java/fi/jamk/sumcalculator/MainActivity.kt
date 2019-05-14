package fi.jamk.sumcalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun sumNumbers(v: View){
        // get number1 and number2 values
        val num1 = editTextNumber2.text.toString().toDouble()
        val num2 = editTextNumber1.text.toString().toDouble()

        // initialize sum
        val sum = num1 + num2

        // display sum
        editTextResult.text = sum.toString()
    }

}
