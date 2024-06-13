package com.example.growwright

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.growwright.utils.*

class MainActivity : AppCompatActivity() {

    private lateinit var modelInterpreter: TFLiteModelInterpreter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        modelInterpreter = TFLiteModelInterpreter(this)

        val genderInput: EditText = findViewById(R.id.genderInput)
        val ageInput: EditText = findViewById(R.id.ageInput)
        val weightInput: EditText = findViewById(R.id.weightInput)
        val heightInput: EditText = findViewById(R.id.heightInput)
        val predictButton: Button = findViewById(R.id.predictButton)
        val resultText: TextView = findViewById(R.id.resultText)

        predictButton.setOnClickListener {
            val gender = if (genderInput.text.toString().lowercase() == "male") 1.0f else 0.0f
            val age = ageInput.text.toString().toFloat()
            val weight = weightInput.text.toString().toFloat()
            val height = heightInput.text.toString().toFloat()

            val normalizedAge = (age - MIN_AGE) / (MAX_AGE - MIN_AGE)
            val normalizedWeight = (weight - MIN_WEIGHT) / (MAX_WEIGHT - MIN_WEIGHT)
            val normalizedHeight = (height - MIN_HEIGHT) / (MAX_HEIGHT - MIN_HEIGHT)


            val input = arrayOf(floatArrayOf(gender, normalizedAge, normalizedWeight, normalizedHeight))

            // Perform prediction
            val output = modelInterpreter.predict(input)

            // Scale or process output as neede

            resultText.text = output[0].joinToString(", ")
        }
//        predictButton.setOnClickListener {
//            val gender = if (genderInput.text.toString().lowercase() == "male") 1.0f else 0.0f
//            val age = ageInput.text.toString().toFloat()
//            val weight = weightInput.text.toString().toFloat()
//            val height = heightInput.text.toString().toFloat()
//
//            val input = arrayOf(floatArrayOf(gender, age, weight, height))
//            val output = modelInterpreter.predict(input)
//
//            // Format the last prediction output if it exists
//            val formattedOutput = if (output.isNotEmpty()) {
//                val lastIndex = output.last()
//                String.format("%.2f%%", lastIndex)
//            } else {
//                "" // Handle case where output is empty (if needed)
//            }
//
//            resultText.text = output.dropLast(1).joinToString(", ") + ", " + formattedOutput
//        }

    }

    override fun onDestroy() {
        modelInterpreter.close()
        super.onDestroy()
    }
}
