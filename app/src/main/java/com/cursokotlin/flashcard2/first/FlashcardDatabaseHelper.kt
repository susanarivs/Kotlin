package com.cursokotlin.flashcard2.first

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper






class FlashcardDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "Flashcards.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        // Crea la tabla "flashcards"
        db.execSQL(
            "CREATE TABLE flashcards (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "categoria TEXT, " +
                    "pregunta TEXT, " +
                    "respuesta TEXT);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}
