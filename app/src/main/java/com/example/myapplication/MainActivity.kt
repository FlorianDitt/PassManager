package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View.INVISIBLE
import android.view.View.generateViewId
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible


class MainActivity : AppCompatActivity() {

    private var userID : String = ""//Get user id from LoginActivity
    private var username : String = ""//Get user id from LoginActivity
    private var showDel : Boolean = false
    private var tableLength: Array<Int> = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (intent.extras != null){
            userID = intent.getStringExtra("UserID").toString()
            username = intent.getStringExtra("Username").toString()
        }
        findViewById<TextView>(R.id.displayUsername).text = username

        val toAddButton : ImageButton = findViewById(R.id.toAddButton)
        val delButton: ImageButton = findViewById(R.id.deleteButton)

        toAddButton.setOnClickListener {
            val intent = Intent(this,AddPasswordActivity::class.java)//Declare target Activity
            intent.putExtra("UserID", userID)//add userID to new Activity
            intent.putExtra("Username", username)
            startActivity(intent)//change to Activity to add Password
        }

        delButton.setOnClickListener {
            showDel = !showDel
            for (i in tableLength.indices) {
                findViewById<ImageButton>(tableLength.elementAt(i)).isVisible = showDel
            }
        }
        createTable()
    }

    private fun createTable(){
        val db = DBHelper(this, null)
        val res = db.getSpecialData(userID.toInt())//Get data from Database

        while(res!!.moveToNext()) {//Store data from Database
            val dataID = res.getString(0)
            val website = res.getString(2)
            val username = res.getString(3)
            val password = res.getString(4)

            val tableLayout : TableLayout = findViewById(R.id.Table)//Get Table layout
            val tblRow = TableRow(this)
            val width = findViewById<TextView>(R.id.WebsiteColumn).width

            val tv1 = TextView(this)
            tv1.text = website
            tv1.gravity = Gravity.CENTER_VERTICAL
            tv1.width = width
            tv1.setPadding(15,15,15,15)
            tblRow.addView(tv1)

            val tv2 = TextView(this)
            tv2.text = username
            tv2.gravity = Gravity.CENTER_HORIZONTAL
            tv2.width = width
            tv2.setPadding(15,15,15,15)
            tblRow.addView(tv2)

            val tv3 = TextView(this)
            tv3.text = password
            tv3.gravity = Gravity.CENTER_HORIZONTAL
            tv3.width = width
            tv3.setPadding(15,15,15,15)
            tblRow.addView(tv3)

            val ib = ImageButton(this)
            ib.id = generateViewId()
            tableLength += ib.id
            ib.visibility = INVISIBLE
            ib.setImageResource(R.drawable.delete)
            ib.setBackgroundColor(Color.TRANSPARENT)
            ib.scaleType = ImageView.ScaleType.FIT_CENTER
            ib.setOnClickListener {
                showDel = !showDel
                for (i in tableLength.indices) {
                    findViewById<ImageButton>(tableLength.elementAt(i)).isVisible = showDel
                }
                val isDataDel:Boolean  = DBHelper(this, null).delData(dataID.toInt())
                if (isDataDel){
                    Toast.makeText(this, "Entry Deleted", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "Entry Not Deleted", Toast.LENGTH_SHORT).show()
                }
                finish()
                overridePendingTransition( 0, 0)
                startActivity(intent)
                overridePendingTransition( 0, 0)

            }
            tblRow.addView(ib)

            tableLayout.addView(tblRow)

        }
    }
}
