package com.c23ps203.roadsignspotter.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.c23ps203.roadsignspotter.R
import com.c23ps203.roadsignspotter.databinding.ActivityResultBinding

@Suppress("DEPRECATION")
class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Road-Sign Spotter" + " | " + "Scan Result"

        val imageUri = intent.getParcelableExtra<Uri>(EXTRA_IMAGE)

        Glide.with(this)
            .load(imageUri)
            .into(binding.ivScanPreview)
        binding.tvLabelPendeteksian.text = intent.getStringExtra(EXTRA_LABEL)

        val confidence = intent.getStringExtra(EXTRA_CONFIDENCE)
        val percentage = confidence?.toDouble()?.times(100)?.toInt()
        binding.tvHasilConfidence.text = "$percentage%"

        binding.btnSelengkapnya.setOnClickListener {
            val url = "https://www.google.com/search?q=Apa itu rambu ${binding.tvLabelPendeteksian.text} 'safetysign' "
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        binding.fabScanAgain.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
            finish()
        }

        when(binding.tvLabelPendeteksian.text) {
            resources.getString(R.string.label_1) -> binding.tvHasilPenjelasan.text = resources.getString(R.string.dilarang_berhenti)
            resources.getString(R.string.label_2) -> binding.tvHasilPenjelasan.text = resources.getString(R.string.dilarang_parkir)
            resources.getString(R.string.label_3) -> binding.tvHasilPenjelasan.text = resources.getString(R.string.larangan_belokkanan)
            resources.getString(R.string.label_4) -> binding.tvHasilPenjelasan.text = resources.getString(R.string.larangan_belokkiri)
            resources.getString(R.string.label_5) -> binding.tvHasilPenjelasan.text = resources.getString(R.string.larangan_memutar)
            resources.getString(R.string.label_6) -> binding.tvHasilPenjelasan.text = resources.getString(R.string.penegasan_rambu_tambahan)
            resources.getString(R.string.label_7) -> binding.tvHasilPenjelasan.text = resources.getString(R.string.isyarat_lalin)
            resources.getString(R.string.label_8) -> binding.tvHasilPenjelasan.text = resources.getString(R.string.area_parkir)
        }
    }

    companion object {
        const val EXTRA_LABEL = "extra_label"
        const val EXTRA_CONFIDENCE = "extra_confidence"
        const val EXTRA_IMAGE = "extra_image"
    }
}