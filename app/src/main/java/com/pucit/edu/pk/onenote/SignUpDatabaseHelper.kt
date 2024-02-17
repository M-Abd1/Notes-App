package com.pucit.edu.pk.onenote

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SignUpDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "quiz.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_SIGNUP = "signup"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_AGE = "age"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createSignUpTableQuery = "CREATE TABLE $TABLE_SIGNUP($COLUMN_NAME TEXT PRIMARY KEY, $COLUMN_EMAIL TEXT, $COLUMN_AGE INTEGER, $COLUMN_PASSWORD TEXT)"
        db?.execSQL(createSignUpTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropSignUpTableQuery = "DROP TABLE IF EXISTS $TABLE_SIGNUP"
        db?.execSQL(dropSignUpTableQuery)
        onCreate(db)
    }

    fun insertSignUp(signUp: SignupModelClass) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, signUp.name)
            put(COLUMN_EMAIL, signUp.email)
            put(COLUMN_AGE, signUp.age)
            put(COLUMN_PASSWORD, signUp.password)
        }
        db.insert(TABLE_SIGNUP, null, values)
        db.close()
    }

    fun getSignUpById(signUpId: Int): SignupModelClass {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_SIGNUP WHERE $COLUMN_NAME = $signUpId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
        val age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE))
        val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))

        cursor.close()
        db.close()
        return SignupModelClass(name, email, age, password)
    }
}