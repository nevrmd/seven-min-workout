package com.nevrmd.sevenminworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nevrmd.sevenminworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

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
    }
}