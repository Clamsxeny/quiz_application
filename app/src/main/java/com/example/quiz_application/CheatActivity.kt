package com.example.quiz_application

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CheatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        val backButton: ImageView = findViewById(R.id.imageView)
        val showAnswerButton: Button = findViewById(R.id.show_answer_button)

        // Set onClickListener for back button
        backButton.setOnClickListener {
            finish() // Close the current activity without sending any result
        }

        // Set onClickListener for show answer button
        showAnswerButton.setOnClickListener {
            // Get the answer passed from MainActivity
            val answer = intent.getBooleanExtra("answer", false)

            // Determine the actual answer text
            val answerText = if (answer) "Benar" else "Salah"

            // Show the actual answer in a Toast message
            Toast.makeText(this, "Jawaban Sebenarnya: $answerText", Toast.LENGTH_SHORT).show()

            // Set the result as OK and finish the activity
            setResult(RESULT_OK)
            finish()
        }

    }
}

