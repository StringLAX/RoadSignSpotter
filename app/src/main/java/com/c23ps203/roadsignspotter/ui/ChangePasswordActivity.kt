package com.c23ps203.roadsignspotter.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.c23ps203.roadsignspotter.R
import com.c23ps203.roadsignspotter.data.helper.PreferenceHelper
import com.c23ps203.roadsignspotter.data.viewmodel.ChangePasswordViewModel
import com.c23ps203.roadsignspotter.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var sharedPref: PreferenceHelper
    private lateinit var viewModel: ChangePasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Road-Sign Spotter" + " | " + "Change Password"

        sharedPref = PreferenceHelper(this)
        viewModel = ViewModelProvider(this)[ChangePasswordViewModel::class.java]
        viewModel.init(sharedPref)

        binding.btnChangePassword.setOnClickListener {
            val currentPassword = binding.edOldPassword.text.toString()
            val newPassword = binding.edNewPassword.text.toString()
            val confirmPassword = binding.edConfirmPassword.text.toString()

            when {
                currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty() -> {
                    binding.edOldPassword.error = resources.getString(R.string.password_empty)
                    binding.edNewPassword.error = resources.getString(R.string.password_empty)
                    binding.edConfirmPassword.error = resources.getString(R.string.password_empty)
                }
                newPassword.length < 8 -> {
                    binding.edNewPassword.error = resources.getString(R.string.password_error)
                }
                newPassword != confirmPassword -> {
                    binding.edNewPassword.error = resources.getString(R.string.password_not_match)
                    binding.edConfirmPassword.error = resources.getString(R.string.password_not_match)
                }
                else -> {
                    showLoading(true)
                    changePassword(currentPassword, newPassword)
                }
            }
        }
    }

    private fun changePassword(currentPassword: String, newPassword: String) {
        viewModel.changePassword(currentPassword, newPassword, {
            showLoading(false)
            Toast.makeText(
                this@ChangePasswordActivity,
                "Password berhasil diubah",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent(this@ChangePasswordActivity, ProfileActivity::class.java))
        }, { errorMessage ->
            showLoading(false)
            Toast.makeText(this@ChangePasswordActivity, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}