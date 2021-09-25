package com.example.sqlitebasic

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "PhoneBook.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "book_table"
        const val COL1_ID = "_id"
        const val COL2_NAME = "name"
        const val COL3_PHONE = "phone"
        const val COL4_EMAIL = "email"

        @Volatile
        private var instance: DatabaseHelper? = null

        fun getInstance(context: Context) =
            instance ?: synchronized(DatabaseHelper::class.java) {
                instance ?: DatabaseHelper(context).also {
                    instance = it
                }
            }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COL1_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL2_NAME TEXT, " +
                "$COL3_PHONE TEXT, " +
                "$COL4_EMAIL TEXT" +
                ")"

        db?.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }

    fun insertData(name: String, phone: String, email: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL2_NAME, name)
            put(COL3_PHONE, phone)
            put(COL4_EMAIL, email)
        }
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun updateData(id: String, name: String, phone: String, email: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL1_ID, id)
            put(COL2_NAME, name)
            put(COL3_PHONE, phone)
            put(COL4_EMAIL, email)
        }
        db.update(TABLE_NAME, contentValues, "$COL1_ID = ?", arrayOf(id))
    }

    fun deleteData(id: String) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COL1_ID = ?", arrayOf(id))
    }

    fun getAllData(): String {
        var result = "No data in DB"

        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        try {
            if (cursor.count != 0) {
                val stringBuffer = StringBuffer()
                while (cursor.moveToNext()) {
                    stringBuffer.append("ID :" + cursor.getInt(0).toString() + "\n")
                    stringBuffer.append("NAME :" + cursor.getString(1) + "\n")
                    stringBuffer.append("PHONE :" + cursor.getString(2) + "\n")
                    stringBuffer.append("EMAIL :" + cursor.getString(3) + "\n\n")
                }
                result = stringBuffer.toString()
            }
        } catch (e:Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return result
    }
}