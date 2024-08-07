package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvTime: TextView
    private lateinit var btnStart: Button
    private lateinit var btnPause: Button
    private lateinit var btnReset: Button

    private var handler = Handler()
    private var milliseconds: Long = 0
    private var running = false
    private var paused = false

    private val runnable = object : Runnable {
        override fun run() {
            milliseconds++
            updateTimer()
            handler.postDelayed(this, 10)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTime = findViewById(R.id.tvTime)
        btnStart = findViewById(R.id.btnStart)
        btnPause = findViewById(R.id.btnPause)
        btnReset = findViewById(R.id.btnReset)

        btnStart.setOnClickListener {
            startTimer()
        }

        btnPause.setOnClickListener {
            pauseTimer()
        }

        btnReset.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer() {
        if (!running) {
            handler.post(runnable)
            running = true
            paused = false
            btnStart.visibility = View.GONE
            btnPause.visibility = View.VISIBLE
        }
    }

    private fun pauseTimer() {
        if (running) {
            handler.removeCallbacks(runnable)
            running = false
            paused = true
            btnStart.visibility = View.VISIBLE
            btnPause.visibility = View.GONE
        }
    }

    private fun resetTimer() {
        handler.removeCallbacks(runnable)
        running = false
        paused = false
        milliseconds = 0
        updateTimer()
        btnStart.visibility = View.VISIBLE
        btnPause.visibility = View.GONE
    }

    private fun updateTimer() {
        val minutes = milliseconds / 6000
        val seconds = (milliseconds / 100) % 60
        val millis = milliseconds % 100

        val timeString = String.format("%02d:%02d:%02d", minutes, seconds, millis)
        tvTime.text = timeString
    }
}
