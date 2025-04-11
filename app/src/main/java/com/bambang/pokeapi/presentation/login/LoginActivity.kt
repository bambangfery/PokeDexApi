package com.bambang.pokeapi.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bambang.pokeapi.databinding.ActivityLoginBinding
import com.bambang.pokeapi.presentation.main.MainActivity
import com.bambang.pokeapi.presentation.register.RegisterActivity
import com.bambang.pokeapi.data.local.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (sessionManager.isLogin()) {
            // langsung ke halaman utama
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(username, password)
            } else {
                Toast.makeText(this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        observeLoginResult()
    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(this) {  result ->
            result.onSuccess { user ->
                sessionManager.saveLoginSession(user.username, user.name)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                showToast("Login Sukses.")
            }.onFailure {
                showToast("Login gagal. Username atau password salah.")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}