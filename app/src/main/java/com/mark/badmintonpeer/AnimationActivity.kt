package com.mark.badmintonpeer

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mark.badmintonpeer.databinding.ActivityAnimationBinding
import kotlinx.coroutines.delay

class AnimationActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_animation)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_animation)

      Handler().postDelayed({
          val intent = Intent(applicationContext, MainActivity::class.java)
//            throw RuntimeException("Test Crash") // Force a crash
          startActivity(intent)
          finish()
      },3000)
        
    }
}