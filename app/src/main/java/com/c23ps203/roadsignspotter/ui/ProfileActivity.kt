package com.c23ps203.roadsignspotter.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.c23ps203.roadsignspotter.R
import com.c23ps203.roadsignspotter.data.helper.Constant
import com.c23ps203.roadsignspotter.data.helper.PreferenceHelper
import com.c23ps203.roadsignspotter.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var sharedPref: PreferenceHelper

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Road-Sign Spotter" + " | " + "Profile"

        sharedPref = PreferenceHelper(this)

        binding.tvName.text = resources.getString(R.string.name_profile) + " ${
            sharedPref.getString(Constant.prefName).toString()
        }"
        binding.tvUsername.text = resources.getString(R.string.username_profile) +
                " ${sharedPref.getString(Constant.prefUsername).toString()}"

        binding.btnChangePassword.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, ChangePasswordActivity::class.java))
        }

        binding.bottomNavigation.selectedItemId = R.id.person
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> {
                    startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.person -> {
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profilemenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_logout -> {
                sharedPref.clear()
                startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}