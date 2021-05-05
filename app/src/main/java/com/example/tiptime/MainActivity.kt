package com.example.tiptime

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonCalculate.setOnClickListener { calculateTip() }

        binding.etCostOfService.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    fun calculateTip() {
        val costString = binding.etCostOfService.text
        if (costString.isEmpty()) {
            Toast.makeText(
                this,
                "Please input Integer Number in Cost of Service !",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        val stringCostOfService = costString.toString()
        val cost: Double = stringCostOfService.toDouble()
        val tipPercentage: Double = when (binding.optionsTip.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            R.id.option_fifteen_percent -> 0.15
            else -> 0.0
        }
        var tip: Double = tipPercentage * cost

        val roundUp = binding.switchOfRoundUp.isChecked

        if (roundUp) {
            tip = kotlin.math.ceil(tip)
        }

        val formattedTip: String = NumberFormat.getCurrencyInstance().format(tip)

        binding.resultTip.text = this.getString(
            R.string.tip_amount,
            formattedTip
        )
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}