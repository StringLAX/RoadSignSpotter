package com.c23ps203.roadsignspotter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.c23ps203.roadsignspotter.R
import com.c23ps203.roadsignspotter.data.helper.Constant
import com.c23ps203.roadsignspotter.data.helper.PreferenceHelper
import com.c23ps203.roadsignspotter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Road-Sign Spotter" + " | " + "Main"

        sharedPref = PreferenceHelper(this)

        binding.textView.text = sharedPref.getString(Constant.prefToken)
        Log.d("token", sharedPref.getString(Constant.prefToken).toString())
    }
}