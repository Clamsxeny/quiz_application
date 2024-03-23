package com.example.quiz_application

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
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
    private lateinit var progressBar: ProgressBar

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
    private var correctAnswersCount = 0
    private val totalQuestions = 8 // Assuming there are 8 questions in total

    // Variables to store the state
    private var savedCurrentQuestionIndex = 0
    private var savedCheatCount = 0
    private var savedCorrectAnswersCount = 0

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
        progressBar = findViewById(R.id.progressBar2)

        // Initialize ProgressBar
        val progressBar: ProgressBar = findViewById(R.id.progressBar2)

        // Set maximum value for ProgressBar
        progressBar.max = totalQuestions

        // Restore state if available
        savedInstanceState?.let {
            savedCurrentQuestionIndex = it.getInt("currentQuestionIndex", 0)
            savedCheatCount = it.getInt("cheatCount", 0)
            savedCorrectAnswersCount = it.getInt("correctAnswersCount", 0)

            currentQuestionIndex = savedCurrentQuestionIndex
            cheatCount = savedCheatCount
            correctAnswersCount = savedCorrectAnswersCount

            displayQuestion()
        }

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
        if (savedInstanceState == null) {
            displayQuestion()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayQuestion() {
        val questionNumber = currentQuestionIndex + 1
        questionNumberTxt.text = "Question $questionNumber/${questions.size}"
        questionTxt.text = questions[currentQuestionIndex]
        progressBar.progress = currentQuestionIndex + 1 // Set ProgressBar progress
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = answers[currentQuestionIndex]
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_answer.also {
                correctAnswersCount++
            }
        } else {
            R.string.incorrect_answer
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        if (currentQuestionIndex == questions.size - 1) {
            // Jika ini adalah pertanyaan terakhir, buka FinalScoreActivity
            val intent = Intent(this, FinalScoreActivity::class.java)
            intent.putExtra("correctAnswers", correctAnswersCount)
            intent.putExtra("totalQuestions", questions.size) // Menambahkan total pertanyaan ke intent
            startActivity(intent)
            // Reset correctAnswersCount agar tidak mempengaruhi aktivitas berikutnya
            correctAnswersCount = 0
            currentQuestionIndex = 0 // Set kembali ke pertanyaan pertama setelah menampilkan skor akhir
        } else {
            // Jika belum, lanjutkan ke pertanyaan berikutnya
            currentQuestionIndex++
            displayQuestion()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                cheatCount++
            }
            if (cheatCount >= 2) {
                // Menambahkan pesan peringatan jika cheat telah digunakan sebanyak 2 kali
                Toast.makeText(this, "Anda hanya dapat menggunakan cheat sebanyak 2x.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentQuestionIndex", currentQuestionIndex)
        outState.putInt("cheatCount", cheatCount)
        outState.putInt("correctAnswersCount", correctAnswersCount)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex", 0)
        cheatCount = savedInstanceState.getInt("cheatCount", 0)
        correctAnswersCount = savedInstanceState.getInt("correctAnswersCount", 0)

        displayQuestion()
    }
}