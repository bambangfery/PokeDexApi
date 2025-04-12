package com.bambang.pokeapi.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.bambang.pokeapi.domain.model.Pokemon
import com.bambang.pokeapi.domain.model.User

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "user_db", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, name TEXT, password TEXT)")
        db.execSQL("CREATE TABLE IF NOT EXISTS pokemon (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, imageUrl TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS pokemon")
        onCreate(db)
    }

    fun insertUser(user: User): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("username", user.username)
            put("name", user.name)
            put("password", user.password)
        }
        return try {
            db.insertOrThrow("users", null, values)
            true
        } catch (e: SQLiteConstraintException) {
            false
        }
    }

    fun getUser(username: String, password: String): User? {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM users WHERE username = ? AND password = ?",
            arrayOf(username, password)
        )

        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                username = cursor.getString(cursor.getColumnIndexOrThrow("username")),
                name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                password = cursor.getString(cursor.getColumnIndexOrThrow("password"))
            )
        }
        cursor.close()
        return user
    }


    fun insertPokemonList(pokemonList: List<Pokemon>) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            for (pokemon in pokemonList) {
                val values = ContentValues().apply {
                    put("name", pokemon.name)
                    put("imageUrl", pokemon.url)
                }
                db.insert("pokemon", null, values)
            }
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
    }

    fun getPokemonList(limit: Int, offset: Int): List<Pokemon> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT name, imageUrl FROM pokemon LIMIT ? OFFSET ?", arrayOf(limit.toString(), offset.toString()))
        val list = mutableListOf<Pokemon>()
        while (cursor.moveToNext()) {
            list.add(
                Pokemon(
                    cursor.getString(0),
                    cursor.getString(1)
                )
            )
        }
        cursor.close()
        return list
    }

    fun getPokemonCount(): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(*) FROM pokemon", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()
        return count
    }

}