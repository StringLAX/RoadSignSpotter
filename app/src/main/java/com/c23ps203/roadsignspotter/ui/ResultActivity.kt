package com.c23ps203.roadsignspotter.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.c23ps203.roadsignspotter.R
import com.c23ps203.roadsignspotter.databinding.ActivityResultBinding
import java.io.File

@Suppress("DEPRECATION")
class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Road-Sign Spotter" + " | " + "Scan Result"

        showLoading(true)
        val imageFile = intent.getSerializableExtra(EXTRA_IMAGE_FILE) as File

        binding.ivScanPreview.setImageURI(Uri.fromFile(imageFile))
        binding.tvLabelPendeteksian.text = intent.getStringExtra(EXTRA_LABEL)

        val confidence = intent.getStringExtra(EXTRA_CONFIDENCE)
        val percentage = confidence?.toDouble()?.times(100)?.toInt()
        binding.tvHasilConfidence.text = "$percentage%"
        showLoading(false)

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

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_LABEL = "extra_label"
        const val EXTRA_CONFIDENCE = "extra_confidence"
        const val EXTRA_IMAGE_FILE = "extra_image_file"
    }
}