package com.nevrmd.sevenminworkout

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import com.nevrmd.sevenminworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var timeForTheExercise: Long = 30
    private var timeForPreparing: Long = 10

    private var player: MediaPlayer? = MediaPlayer()

    private lateinit var binding: ActivityExerciseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.tbExerciseActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.tbExerciseActivity.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        exerciseList = Constants.defaultExerciseList()
        setupRestView()
    }

    override fun onDestroy() {
        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if(player != null) {
            player!!.stop()
        }

        super.onDestroy()
    }

    private fun setRestProgressBar() {
        binding.progressBar.progress = restProgress

        restTimer = object: CountDownTimer(timeForPreparing * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                val timeToDisplay = timeForPreparing.toInt() - restProgress
                binding.progressBar.progress = timeToDisplay
                binding.tvTimer.text = "$timeToDisplay"
            }

            override fun onFinish() {
                currentExercisePosition++
                setupExerciseView()
            }
        }.start()
    }

    private fun setupRestView () {
        try {
            player = MediaPlayer.create(applicationContext, R.raw.pluh)
            player!!.isLooping = false
            player!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.llRestView.visibility = View.VISIBLE
        binding.llExerciseView.visibility = View.GONE

        if(restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        binding.tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()

        setRestProgressBar()
    }

    private fun setExerciseProgressBar() {
        binding.progressBarExercise.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(timeForTheExercise * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                val timeToDisplay = timeForTheExercise.toInt() - exerciseProgress
                binding.progressBarExercise.progress = timeToDisplay
                binding.tvExerciseTimer.text = "$timeToDisplay"
            }

            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! - 1){
                    setupRestView()
                } else {
                    Toast.makeText(
                        this@ExerciseActivity,
                        "You have completed the 7 minutes workout! Great job!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }

    private fun setupExerciseView() {

        binding.llRestView.visibility = View.GONE
        binding.llExerciseView.visibility = View.VISIBLE

        if(exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        setExerciseProgressBar()

        binding.ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }
}