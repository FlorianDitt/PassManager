package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditActivity : AppCompatActivity() {


    private var userID : String = ""//Get user id from LoginActivity
    private var username : String = ""//Get user id from LoginActivity
    private var passwordID: String = ""
    private var website: String = ""
    private var savedUsername: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        if (intent.extras != null){
            userID = intent.getStringExtra("UserID").toString()
            username = intent.getStringExtra("Username").toString()
            passwordID = intent.getStringExtra("PasswordID").toString()
            website = intent.getStringExtra("Website").toString()
            savedUsername = intent.getStringExtra("savedUsername").toString()
            password = intent.getStringExtra("Password").toString()
        }

        val editWebsite = findViewById<EditText>(R.id.editWebsite)
        val editUsername = findViewById<EditText>(R.id.editUsername)
        val editPassword =findViewById<EditText>(R.id.editPassword)

        editWebsite.setText(website)
        editUsername.setText(username)
        editPassword.setText(password)


        findViewById<Button>(R.id.btnSubmitEdit).setOnClickListener {
            if (editWebsite.text.toString() != "" && editUsername.text.toString() != "" && editPassword.text.toString() != ""){
                val db = DBHelper(this, null)
                db.updateData(passwordID.toInt(), editWebsite.text.toString(), editUsername.text.toString(), editPassword.text.toString())
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("UserID", userID)
                intent.putExtra("Username", username)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Could not update", Toast.LENGTH_SHORT).show()
            }
        }
    }
}