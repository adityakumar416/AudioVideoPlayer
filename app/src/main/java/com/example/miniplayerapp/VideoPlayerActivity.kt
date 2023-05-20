package com.example.miniplayerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.activity.result.contract.ActivityResultContracts
import com.example.miniplayerapp.databinding.ActivityVideoPlayerBinding
import java.util.Timer
import java.util.TimerTask

class VideoPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoPlayerBinding
    private val PICK_VIDEO_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chooseFile.setOnClickListener {
            picVideoFromGallery()
        }

        binding.playPause.setOnClickListener {
            playPauseButtonClick()
        }

        binding.videoView.setOnPreparedListener {
                videoPlayer->
            binding.seekBar.max=videoPlayer.duration
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser){
                    binding.videoView.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                binding.videoView.pause()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                binding.videoView.start()
            }
        })

    }

    private fun playPauseButtonClick() {
        if(binding.videoView?.isPlaying()==true){
            binding.videoView?.pause()
            binding.playPause.setText("Play")
        }
        else{
            binding.videoView?.start()
            binding.playPause.setText("Pause")
        }
    }

    private fun picVideoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "video/*"
        startActivityForResult(intent, PICK_VIDEO_REQUEST_CODE)
    /* val launchingActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        }
        launchingActivity.launch(intent)*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_VIDEO_REQUEST_CODE && requestCode == RESULT_OK){
            val videoUri = data?.data
            binding.videoView.requestFocus()
            binding.videoView.setVideoURI(videoUri)

            binding.videoView.setOnCompletionListener {
                binding.videoView?.seekTo(0)
                binding.seekBar.progress=0
            }
            startVideoPlayer()

        }
    }


    private fun startVideoPlayer() {
        binding.videoView.start()

        val duration = binding.videoView?.duration?:0

        binding.seekBar.max= duration

        Timer().scheduleAtFixedRate(object : TimerTask(){
            override fun run() {
                runOnUiThread {
                    val currentPosition = binding.videoView?.currentPosition?:0
                    binding.seekBar.progress = currentPosition
                }
            }

        },0,1000)
    }
    private fun stopVideoPlayer(){
        binding.videoView.stopPlayback()
        binding.seekBar.progress=0
    }


    override fun onStop() {
        super.onStop()
        stopVideoPlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}