package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var userID : String = ""//Get user id from LoginActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (intent.extras != null){
            userID = intent.getStringExtra("UserID").toString()
        }

        val toAddButton : Button = findViewById(R.id.toAddButton)

        toAddButton.setOnClickListener {
            val intent = Intent(this,AddPasswordActivity::class.java)//Declare target Activity
            intent.putExtra("UserID", userID)//add userID to new Activity
            startActivity(intent)//change to Activity to add Password
        }

        createTable()
    }

    private fun createTable(){
        val db = DBHelper(this, null)
        println("Main: " + userID.toInt())
        val res = db.getSpecialData(userID.toInt())//Get data from Database

        while(res!!.moveToNext()) {//Store data from Database
            val website = res.getString(2)
            val username = res.getString(3)
            val password = res.getString(4)

            val tableLayout : TableLayout = findViewById(R.id.Table)//Get Table layout
            val tblRow = TableRow(this)

            val tv1 = TextView(this)
            tv1.text = website
            tv1.gravity = Gravity.CENTER_HORIZONTAL
            tv1.setPadding(15,15,15,15)
            tblRow.addView(tv1)

            val tv2 = TextView(this)
            tv2.text = username
            tv2.gravity = Gravity.CENTER_HORIZONTAL
            tv2.setPadding(15,15,15,15)
            tblRow.addView(tv2)

            val tv3 = TextView(this)
            tv3.text = password
            tv3.gravity = Gravity.CENTER_HORIZONTAL
            tv3.setPadding(15,15,15,15)
            tblRow.addView(tv3)

            tableLayout.addView(tblRow)

        }
    }
}