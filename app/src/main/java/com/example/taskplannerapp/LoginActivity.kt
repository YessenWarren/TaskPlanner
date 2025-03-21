package com.example.taskplannerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.taskplannerapp.MainActivity
import com.example.taskplannerapp.R
import com.example.taskplannerapp.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var lottieAnimation: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        lottieAnimation = findViewById(R.id.lottieAnimation)

        // Анимация появления элементов
        usernameEditText.alpha = 0f
        passwordEditText.alpha = 0f
        loginButton.alpha = 0f
        registerButton.alpha = 0f

        usernameEditText.animate().alpha(1f).setDuration(800).start()
        passwordEditText.animate().alpha(1f).setDuration(1000).start()
        loginButton.animate().alpha(1f).setDuration(1200).start()
        registerButton.animate().alpha(1f).setDuration(1400).start()

        // Анимация при нажатии на кнопку входа
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val savedUsername = sharedPreferences.getString("username", null)
            val savedPassword = sharedPreferences.getString("password", null)

            if (username == savedUsername && password == savedPassword) {
                lottieAnimation.playAnimation()  // Запускаем анимацию при успешном входе
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
