package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DATABASE_NAME = "PassManager.db" //Name of the Database
private const val DATABASE_VERSION = 2 //Version of the Database

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    val table1Name = "Login" //Name of table 1
    private val table2Name = "Saves" //Name of table 2

    private val table1Create =
        "CREATE TABLE $table1Name (ID INTEGER PRIMARY KEY AUTOINCREMENT, Username TEXT, Password TEXT)" //SQL Statement to create Table 1
    private val table2Create =
        "CREATE TABLE $table2Name (ID INTEGER PRIMARY KEY AUTOINCREMENT, User INTEGER, Website TEXT, Username TEXT, Password TEXT)" //SQL Statement to create Table 2

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(table1Create)//Create Table 1 every new version
        db?.execSQL(table2Create)//Create Table 2 every new version
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $table1Name")//Delete old Table
        db.execSQL("DROP TABLE IF EXISTS $table2Name")//Delete old Table
        onCreate(db)
    }

    fun getData(TABLE_NAME : String): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun getSpecialData(UserNumber : Int): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT * FROM $table2Name WHERE User LIKE $UserNumber GROUP BY Website", null)
    }

    fun insertLogin(Username: String, Password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put("Username", Username)
        contentValues.put("Password", Password)

        return db.insert(table1Name, null, contentValues)
    }

    fun insertSave(user : String, website: String, Username: String, Password: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put("User", user)
        contentValues.put("Website", website)
        contentValues.put("Username", Username )
        contentValues.put("Password", Password )

        return db.insert(table2Name, null, contentValues)
    }
}
