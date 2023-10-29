package com.cursokotlin.flashcard2.first

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.cursokotlin.flashcard2.R

class FirstAppActivity : AppCompatActivity() {

    private var preguntaCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_app)
        supportActionBar?.hide()

        val categoriaEditText = findViewById<EditText>(R.id.textcategoria)
        val preguntaEditText = findViewById<EditText>(R.id.textpregunta)
        val respuestaEditText = findViewById<EditText>(R.id.textrespuesta)
        val siguienteButton = findViewById<Button>(R.id.siguientepregunta)
        val guardarButton = findViewById<Button>(R.id.botonguardar)

        siguienteButton.setOnClickListener {
            val categoria = categoriaEditText.text.toString()
            val pregunta = preguntaEditText.text.toString()
            val respuesta = respuestaEditText.text.toString()

            if (preguntaCount < 10 && categoria.isNotEmpty() && pregunta.isNotEmpty() && respuesta.isNotEmpty()) {
                Log.d(
                    "Debug",
                    "Pregunta guardada - Categoria: $categoria, Pregunta: $pregunta, Respuesta: $respuesta"
                )

                val dbHelper = FlashcardDatabaseHelper(this)
                val db = dbHelper.writableDatabase

                val values = ContentValues()
                values.put("categoria", categoria)
                values.put("pregunta", pregunta)
                values.put("respuesta", respuesta)

                val newRowId = db.insert("flashcards", null, values)

                preguntaCount++
                preguntaEditText.text.clear()
                respuestaEditText.text.clear()
            } else {

                Toast.makeText(this, "Ingrese datos en todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        guardarButton.setOnClickListener {
            val categoria = categoriaEditText.text.toString()
            val pregunta = preguntaEditText.text.toString()
            val respuesta = respuestaEditText.text.toString()

            if (categoria.isNotEmpty() && pregunta.isNotEmpty() && respuesta.isNotEmpty()) {
                Log.d(
                    "Debug",
                    "Pregunta guardada - Categoria: $categoria, Pregunta: $pregunta, Respuesta: $respuesta"
                )

                val dbHelper = FlashcardDatabaseHelper(this)
                val db = dbHelper.writableDatabase

                val values = ContentValues()
                values.put("categoria", categoria)
                values.put("pregunta", pregunta)
                values.put("respuesta", respuesta)

                val newRowId = db.insert("Flashcards", null, values)

                categoriaEditText.isEnabled = false
                preguntaEditText.isEnabled = false
                siguienteButton.isEnabled = false
                respuestaEditText.isEnabled = false

                /// Iniciar la nueva actividad y pasar datos
                val intent = Intent(this, RondasAppActivity::class.java)
                intent.putExtra("categoria", categoria)
                intent.putExtra("pregunta", pregunta)
                intent.putExtra("respuesta", respuesta)
                startActivity(intent)
            } else {

                Toast.makeText(this, "Ingrese datos en todos los campos", Toast.LENGTH_SHORT).show()
            }
        }


    }
}