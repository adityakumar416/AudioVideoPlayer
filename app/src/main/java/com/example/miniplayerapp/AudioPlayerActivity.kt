package com.example.miniplayerapp

import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
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
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "audio/*"
            startActivityForResult(intent,1)
        }
        binding.playPause.setOnClickListener {
            if (isPlaying) {
                mediaPlayer.pause()
                binding.playPause.text = "play"
                isPlaying = false
            }
            else{
                mediaPlayer.start()
                binding.playPause.text = "pause"
                isPlaying = true
            }
        }
        mediaPlayer.setOnPreparedListener {
            mediaPlayer.start()
            binding.playPause.text = "pause"
            isPlaying = true
        }

        mediaPlayer.setOnCompletionListener {
            isPlaying = false
            binding.playPause.text = "play"
        }



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == RESULT_OK){
            val uri = data?.data?:return
            mediaPlayer.reset()
            mediaPlayer.setDataSource(applicationContext,uri)
            mediaPlayer.prepare()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

}