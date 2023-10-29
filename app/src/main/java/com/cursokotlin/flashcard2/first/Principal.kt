package com.cursokotlin.flashcard2.first

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cursokotlin.flashcard2.R

import android.content.Intent
import android.widget.Button

class Principal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        supportActionBar?.hide()

        val jugarButton = findViewById<Button>(R.id.jugar)

        jugarButton.setOnClickListener {
            val intent = Intent(this, FirstAppActivity::class.java)
            startActivity(intent)
        }
    }
}
