package com.sumitt.findhome.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.sumitt.findhome.R

/**
 * Activity to display splash at start
 * @author sumit.T
 * */
class SplashActivity : AppCompatActivity() {
    lateinit var mSplashView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        mSplashView = findViewById(R.id.splash_container)
        mSplashView.setAlpha(0f)
        mSplashView.animate().setDuration(SPLASH_TIME).setStartDelay(500L).alpha(1f).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                goToHome()
            }
        })
    }

    //go to home after splash
    private fun goToHome() {
        mSplashView.setAlpha(1f)
        mSplashView.animate().setDuration(500L).setStartDelay(SPLASH_TIME).alpha(0f).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        })
    }

    companion object {
        //default splash display time
        const val SPLASH_TIME = 2000L
    }
}