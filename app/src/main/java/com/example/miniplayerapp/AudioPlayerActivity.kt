package com.example.miniplayerapp

import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.example.miniplayerapp.databinding.ActivityAudioPlayerBinding
import com.example.miniplayerapp.databinding.ActivityVideoPlayerBinding
import java.util.*

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
        mediaPlayer.setOnPreparedListener {audioPlayer->
            mediaPlayer.start()
            binding.playPause.text = "pause"
            isPlaying = true
            binding.seekBar.max=audioPlayer.duration
        }

        mediaPlayer.setOnCompletionListener {
            isPlaying = false
            binding.playPause.text = "play"
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
               mediaPlayer.pause()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mediaPlayer.start()
            }
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 1 && resultCode == RESULT_OK){
            val uri = data?.data?:return
            mediaPlayer.reset()
            mediaPlayer.setDataSource(applicationContext,uri)
            mediaPlayer.prepare()

            mediaPlayer.setOnCompletionListener {
                mediaPlayer.seekTo(0)
                binding.seekBar.progress=0
            }
                startAudioPlayer()
        }
    }

    private fun startAudioPlayer() {
        mediaPlayer.start()
        val duration = mediaPlayer.duration?:0
        binding.seekBar.max = duration
        Timer().scheduleAtFixedRate(object : TimerTask(){
            override fun run() {
                val currentPosition = mediaPlayer.currentPosition?:0
                binding.seekBar.progress = currentPosition
            }

        },0,1000)
    }

    override fun onStop() {
        super.onStop()
        stopAudioPlayer()
    }

    private fun stopAudioPlayer() {
        mediaPlayer.stop()
        binding.seekBar.progress = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

}