package com.cursokotlin.flashcard2.first

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.cursokotlin.flashcard2.R

class RondasAppActivity : AppCompatActivity() {
    private lateinit var rondasListView: ListView
    private lateinit var rondasAdapter: ArrayAdapter<String>
    private val rondas: MutableList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rondas_app)
        supportActionBar?.hide()
        rondasListView = findViewById(R.id.rondasListView)
        configurarListaDeRondas()
    }

    @SuppressLint("Range")
    private fun configurarListaDeRondas() {
        val dbHelper = FlashcardDatabaseHelper(this)
        val db = dbHelper.readableDatabase

        val projection = arrayOf("categoria")

        val cursor = db.query(
            true,
            "flashcards",
            projection,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()) {
            val categoria = cursor.getString(cursor.getColumnIndex("categoria"))
            rondas.add(categoria)
        }

        rondasAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, rondas)
        rondasListView.adapter = rondasAdapter

        rondasListView.setOnItemClickListener { parent, view, position, id ->

        cursor.close()

        rondasAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, rondas)
        rondasListView.adapter = rondasAdapter

        rondasListView.setOnItemClickListener { parent, view, position, id ->
            val categoriaSeleccionada = rondas[position]
            val intent = Intent(this, SecondAppActivity::class.java)
            intent.putExtra("categoria", categoriaSeleccionada)
            startActivity(intent)
        }
    }

        rondasListView.setOnItemLongClickListener { parent, view, position, id ->
            val rondaAEliminar = rondas[position]
            eliminarRonda(rondaAEliminar)
            return@setOnItemLongClickListener true
        }
    }

    private fun eliminarRonda(ronda: String) {
        val dbHelper = FlashcardDatabaseHelper(this)
        val db = dbHelper.writableDatabase

        val whereClause = "categoria = ?"
        val whereArgs = arrayOf(ronda)

        db.delete("flashcards", whereClause, whereArgs)

        // Actualizar la lista de rondas después de la eliminación
        rondas.remove(ronda)
        rondasAdapter.notifyDataSetChanged()
    }
}



