package com.mark.badmintonpeer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mark.badmintonpeer.databinding.ActivityAnimationBinding

class AnimationActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_animation)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_animation)

        binding.animationView.setOnClickListener {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)

            }

    }
}