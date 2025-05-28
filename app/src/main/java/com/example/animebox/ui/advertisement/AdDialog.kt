package com.example.animebox.ui.advertisement

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.animebox.R

class AdDialog(
    context: Context,
    private val onAdCompleted: () -> Unit
) : Dialog(context) {

    private lateinit var timerTextView: TextView
    private lateinit var adTimer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.ad_dialog)
        setCancelable(false)

        timerTextView = findViewById(R.id.timerTextView)
        val adImageView = findViewById<ImageView>(R.id.adImageView)
        adImageView.setImageResource(R.drawable.ad_image)

        startTimer()
    }

    private fun startTimer() {
        adTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timerTextView.text = "Осталось: ${millisUntilFinished / 1000} сек"
            }

            override fun onFinish() {
                dismiss()
                onAdCompleted()
            }
        }.start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (::adTimer.isInitialized) {
            adTimer.cancel()
        }
    }
} 