package com.c23ps203.roadsignspotter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.c23ps203.roadsignspotter.R
import com.c23ps203.roadsignspotter.databinding.ActivityLoginBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}