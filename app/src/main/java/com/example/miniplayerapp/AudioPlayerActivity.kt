package com.example.miniplayerapp

import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.miniplayerapp.databinding.ActivityAudioPlayerBinding
import com.example.miniplayerapp.databinding.ActivityVideoPlayerBinding

class AudioPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAudioPlayerBinding
    private lateinit var mediaPlayer: MediaPlayer
    private var isPlaying = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAudioPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer()
        binding.selectAudio.setOnClickListener {

        }

    }
}