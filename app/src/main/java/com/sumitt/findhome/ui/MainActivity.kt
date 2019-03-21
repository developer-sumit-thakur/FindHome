package com.sumitt.findhome.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.sumitt.findhome.R
import com.sumitt.findhome.ui.fragments.ShowHomesFragment

/**
 * Activity class to show UI
 * @author sumit.T
 * */
class MainActivity : AppCompatActivity(), ShowHomesFragment.FragmentListener {
    override fun onSuccess() {
        progress_container?.apply {
            visibility = View.GONE
        }
        errorText?.apply {
            setText("")
        }
    }

    override fun onError() {
        progress_container?.apply {
            visibility = View.GONE
        }
        errorText?.apply {
            setText(R.string.error_string)
        }
    }

    lateinit var errorText: TextView

    lateinit var container: FrameLayout

    lateinit var progress_container: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        errorText = findViewById(R.id.error_text)
        container = findViewById(R.id.fragment_container)
        progress_container = findViewById(R.id.loadingBar)
        progress_container.visibility = View.VISIBLE

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                    ShowHomesFragment(), ShowHomesFragment.TAG).commit()
        }
    }
}
