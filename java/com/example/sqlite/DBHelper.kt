package com.example.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context, factory: CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "PERSON_DATABASE"
        private const val DATABASE_VERSION = 3
        const val TABLE_NAME = "person_table"
        const val KEY_ID = "id"
        const val KEY_NAME = "name"
        const val KEY_LAST_NAME = "lastname"
        const val KEY_AGE = "age"
        const val KEY_ROLE = "role"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_LAST_NAME + " TEXT, " +
                KEY_AGE + " TEXT, " +
                KEY_ROLE + " TEXT" + ")")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addName(name: String, lastname: String, age: String, role: String) {
        val values = ContentValues().apply {
            put(KEY_NAME, name)
            put(KEY_LAST_NAME, lastname)
            put(KEY_AGE, age)
            put(KEY_ROLE, role)
        }
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getInfo(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun removeAll() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }
}