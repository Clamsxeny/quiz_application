package com.example.quiz_application

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FinalScoreActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_final_score)

        val finalScoreTextView: TextView = findViewById(R.id.finalScoreTextView)
        val returnToQuizButton: Button = findViewById(R.id.returnToQuizButton)

        // Get the number of correct answers passed from MainActivity
        val correctAnswers = intent.getIntExtra("correctAnswers", 0)
        // Get the total number of questions passed from MainActivity
        val totalQuestions = intent.getIntExtra("totalQuestions", 0)

        // Set the final score text
        finalScoreTextView.text = "Final Score: $correctAnswers / $totalQuestions"

        // Set click listener for return button
        returnToQuizButton.setOnClickListener {
            // Close this activity and start the main quiz activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Close this activity
        }
    }
}


