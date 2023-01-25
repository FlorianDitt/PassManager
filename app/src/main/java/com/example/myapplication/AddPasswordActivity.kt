package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddPasswordActivity : AppCompatActivity() {

    private var userID : String = ""//Get user id from MainActivity
    private var intentUsername : String = ""//Get user id from LoginActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_password)
        if (intent.extras != null){
            userID = intent.getStringExtra("UserID").toString()
            intentUsername = intent.getStringExtra("Username").toString()
        }

        findViewById<Button>(R.id.SubmitButton).setOnClickListener {
            val db = DBHelper(this, null)
            val website: String = findViewById<EditText>(R.id.websiteInput).text.toString()
            val username: String = findViewById<EditText>(R.id.usernameInput).text.toString()
            val password: String = findViewById<EditText>(R.id.passwordInput).text.toString()
            if (website != "" && username != "" && password != "") {
                db.insertSave(
                    userID,
                    website,
                    username,
                    password
                )
                println("added")
                val intent = Intent(this,MainActivity::class.java)//Declare target Activity
                intent.putExtra("UserID", userID)//add userID to new Activity
                intent.putExtra("Username", intentUsername)
                startActivity(intent)//change to Activity to add Password
            }
        }
    }
}