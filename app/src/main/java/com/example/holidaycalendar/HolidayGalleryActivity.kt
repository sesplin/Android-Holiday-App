package com.example.holidaycalendar

import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.holidaycalendar.HolidayFragment.Companion.newInstance


class HolidayGalleryActivity : AppCompatActivity(), HolidayListFragment.Callbacks {

    private lateinit var sunView: View
    var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_holiday_list)

        sunView = findViewById(R.id.imageView)
        sunView.setOnClickListener{
            startAnimation()
            playSound()
        }

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.fragmentContainer, HolidayListFragment.newInstance())
                    .commit()
        }
    }

    fun playSound() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.fireworksound)
            mMediaPlayer!!.isLooping = false
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    private fun startAnimation(){
        val sunYstart = sunView.top.toFloat()
        val sunYend = sunView.height.toFloat()

        val heightAnimator = ObjectAnimator
                .ofFloat(sunView, "y", sunYstart, sunYend)
                .setDuration(3000)

        heightAnimator.start()
    }

    override fun onGalleryItemSelected(holiday: Holiday){
        Log.d("HolidayGalleryActivity", "Holiday was selected: ${holiday.name}")
        val fragment = HolidayFragment.newInstance(holiday)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
    }
}