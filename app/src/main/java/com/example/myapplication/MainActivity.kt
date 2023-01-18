package com.example.myapplication

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var websitearr : Array<String> = arrayOf()//Array of all Websites in the table
    private var usernamearr : Array<String> = arrayOf()//Array of all Usernames in the Table
    private var passwordarr : Array<String> = arrayOf()//Array of Password in the Table
    private val bundle :Bundle ? = intent.extras
    private var userID:String = intent!!.getStringExtra("UserID").toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (bundle != null){
            userID = bundle.getString("UserID").toString()
        }


        getUserData()
        createTable()
    }

    private fun createTable(){

        val tableLayout : TableLayout = findViewById(R.id.Table)//Table layout

        for (i in 1 until websitearr.size) {
            val tblRow = TableRow(this)
            val tv1 = TextView(this)
            tv1.text = websitearr.elementAt(i)
            val tv2 = TextView(this)
            tv2.text = usernamearr.elementAt(i)
            val tv3 = TextView(this)
            tv3.text = passwordarr.elementAt(i)
            tableLayout.addView(tblRow)
        }
    }

    private fun getUserData(){
        val db = DBHelper(this, null)
        println("Main: $userID.toInt")
        val res = db.getSpecialData(LoginActivity().pID)//Get data from Database

        while(res!!.moveToNext()) {//Store data from Database
            val website = res.getString(2)
            val username = res.getString(3)
            val password = res.getString(4)
            websitearr += website
            usernamearr += username
            passwordarr += password
        }
    }
}