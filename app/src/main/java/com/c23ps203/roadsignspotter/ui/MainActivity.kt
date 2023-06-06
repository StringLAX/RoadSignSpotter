package com.c23ps203.roadsignspotter.ui

import android.annotation.SuppressLint
import android.content.Intent
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

    @SuppressLint("SetTextI18n")
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Road-Sign Spotter" + " | " + "Dashboard"

        sharedPref = PreferenceHelper(this)

        binding.tvName.text = sharedPref.getString(Constant.prefName).toString()

        Log.d("token", sharedPref.getString(Constant.prefToken).toString())
        Log.d("name", sharedPref.getString(Constant.prefName).toString())
        Log.d("username", sharedPref.getString(Constant.prefUsername).toString())
        Log.d("email", sharedPref.getString(Constant.prefEmail).toString())
        Log.d("userId", sharedPref.getString(Constant.prefUserId).toString())

        binding.btnToCameraActivity.setOnClickListener {
            startActivity(Intent(this@MainActivity, ScanActivity::class.java))
        }

        binding.bottomNavigation.selectedItemId = R.id.dashboard
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.person -> {
                    startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }
}