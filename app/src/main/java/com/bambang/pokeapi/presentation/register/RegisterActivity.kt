package com.bambang.pokeapi.presentation.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bambang.pokeapi.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val name = binding.etName.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty()) {
                viewModel.register(username, name, password)
            } else {
                Toast.makeText(this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.registerResult.observe(this) { result ->
            result.onSuccess { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                finish() // kembali ke LoginActivity
            }

            result.onFailure { error ->
                Toast.makeText(this, error.message ?: "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}