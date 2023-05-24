package com.example.miniplayerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.miniplayerapp.databinding.ActivityAudioPlayerBinding
import com.example.miniplayerapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.video.setOnClickListener {
            val intent = Intent(this,VideoPlayerActivity::class.java)
            startActivity(intent)
        }


        binding.audio.setOnClickListener {
            val intent = Intent(this,AudioPlayerActivity::class.java)
            startActivity(intent)
        }

    }
}