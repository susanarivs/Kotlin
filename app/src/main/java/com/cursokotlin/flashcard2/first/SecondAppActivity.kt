package com.cursokotlin.flashcard2.first
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.cursokotlin.flashcard2.R

class SecondAppActivity : AppCompatActivity() {
    private lateinit var preguntaView: TextView
    private lateinit var respuestaButton: Button
    private lateinit var izquierdaButton: Button
    private lateinit var derechaButton: Button
    private lateinit var categoriaView: TextView
    private var preguntas: MutableList<String> = ArrayList()
    private var respuestas: MutableList<String> = ArrayList()
    private var preguntaActual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_app)
        supportActionBar?.hide()

        categoriaView = findViewById(R.id.categoriaview)
        preguntaView = findViewById(R.id.preguntaview)
        respuestaButton = findViewById(R.id.botonrespuesta)
        izquierdaButton = findViewById(R.id.izquierdaboton)
        derechaButton = findViewById(R.id.derechaboton)
        categoriaView.text = intent.getStringExtra("categoria")

        configurarPreguntas()
    }

    @SuppressLint("Range")
    private fun configurarPreguntas() {
        val dbHelper = FlashcardDatabaseHelper(this)
        val db = dbHelper.readableDatabase

        val categoriaSeleccionada = intent.getStringExtra("categoria")

        val projection = arrayOf("pregunta", "respuesta")
        val selection = "categoria = ?"
        val selectionArgs = arrayOf(categoriaSeleccionada)

        val cursor = db.query(
            "flashcards",
            projection,
            selection,
            selectionArgs,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val pregunta = cursor.getString(cursor.getColumnIndex("pregunta"))
            val respuesta = cursor.getString(cursor.getColumnIndex("respuesta"))
            preguntas.add(pregunta)
            respuestas.add(respuesta)
        }

        cursor.close()

        if (preguntas.isNotEmpty()) {
            val preguntasMezcladas = preguntas.zip(respuestas).shuffled()
            preguntas = preguntasMezcladas.map { it.first }.toMutableList()
            respuestas = preguntasMezcladas.map { it.second }.toMutableList()

            preguntaView.text = preguntas[preguntaActual]
            respuestaButton.text = "Mostrar respuesta"
        }

        respuestaButton.setOnClickListener {
            if (respuestaButton.text == "Mostrar respuesta") {
                preguntaView.text = respuestas[preguntaActual]
                respuestaButton.text = "Ocultar respuesta"
            } else {
                preguntaView.text = preguntas[preguntaActual]
                respuestaButton.text = "Mostrar respuesta"
            }
        }

        izquierdaButton.setOnClickListener {
            if (preguntaActual > 0) {
                preguntaActual--
                preguntaView.text = preguntas[preguntaActual]
                respuestaButton.text = "Mostrar respuesta"
            }
        }

        derechaButton.setOnClickListener {
            if (preguntaActual < preguntas.size - 1) {
                preguntaActual++
                preguntaView.text = preguntas[preguntaActual]
                respuestaButton.text = "Mostrar respuesta"
            }
        }
    }
}
