package com.example.quiz_application

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var cheatButton: Button
    private lateinit var leftArrow: ImageButton
    private lateinit var rightArrow: ImageButton
    private lateinit var questionNumberTxt: TextView
    private lateinit var questionTxt: TextView

    private val questions = arrayOf(
        "Gunung Fuji adalah gunung tertinggi di Jepang.",
        "Pembangunan Menara Eiffel selesai pada 31 Maret 1887",
        "Petir terlihat sebelum terdengar karena cahaya merambat lebih cepat daripada suara.",
        "Kotak hitam di pesawat berwarna hitam.",
        "Cleopatra adalah keturunan Mesir.",
        "Google awalnya bernama BackRub.",
        "Bola lampu adalah penemuan Thomas Edison.",
        "Kota Vatikan adalah sebuah negara."
    )

    private val answers = arrayOf(
        true,
        false,
        true,
        false,
        false,
        true,
        false,
        true
    )

    private var currentQuestionIndex = 0
    private var cheatCount = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        trueButton = findViewById(R.id.trueButton)
        falseButton = findViewById(R.id.falseButton)
        cheatButton = findViewById(R.id.cheatButton)
        leftArrow = findViewById(R.id.leftArrow)
        rightArrow = findViewById(R.id.rightArrow)
        questionNumberTxt = findViewById(R.id.questionNumberTxt)
        questionTxt = findViewById(R.id.questionTxt)

        // Set click listeners
        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        cheatButton.setOnClickListener {
            if (cheatCount < 2) {
                val intent = Intent(this, CheatActivity::class.java)
                intent.putExtra("answer", answers[currentQuestionIndex])
                startActivityForResult(intent, 1)
            } else {
                Toast.makeText(this, "Anda hanya dapat menggunakan cheat sebanyak 2x.", Toast.LENGTH_SHORT).show()
            }
        }

        leftArrow.setOnClickListener {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--
                displayQuestion()
            }
        }

        rightArrow.setOnClickListener {
            if (currentQuestionIndex < questions.size - 1) {
                currentQuestionIndex++
                displayQuestion()
            }
        }

        // Display initial question
        displayQuestion()
    }

    @SuppressLint("SetTextI18n")
    private fun displayQuestion() {
        val questionNumber = currentQuestionIndex + 1
        questionNumberTxt.text = "Question $questionNumber/${questions.size}"
        questionTxt.text = questions[currentQuestionIndex]
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = answers[currentQuestionIndex]
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_answer
        } else {
            R.string.incorrect_answer
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            cheatCount++
        }
    }
}
