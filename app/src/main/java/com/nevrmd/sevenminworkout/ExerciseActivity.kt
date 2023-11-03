package com.nevrmd.sevenminworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.nevrmd.sevenminworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private lateinit var binding: ActivityExerciseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.tbExerciseActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tbExerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()
    }

    override fun onDestroy() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        super.onDestroy()
    }

    private fun setRestProgressBar() {
        binding.progressBar.progress = restProgress
        restTimer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                val timeToDisplay = 10-restProgress
                binding.progressBar.progress = timeToDisplay
                binding.tvTimer.text = "$timeToDisplay"
            }

            override fun onFinish() {
                Toast.makeText(
                    this@ExerciseActivity,
                    "Now you can start the exercise",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }

    private fun setupRestView () {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }
}