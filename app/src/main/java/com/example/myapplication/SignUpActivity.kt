package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignUpActivity : AppCompatActivity() {

    private var checkUsername: Array<String> = arrayOf("")//Array for every username

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sine_up)

        val usernameInput = findViewById<EditText>(R.id.username)//Input field of username
        val password1Input = findViewById<EditText>(R.id.password1)//Input field of password1
        val password2Input = findViewById<EditText>(R.id.password2)//Input field of password2
        val sineUpInput = findViewById<Button>(R.id.submit)//Sign up Button

        sineUpInput.setOnClickListener {//what happens when Sign Up button is clicked
            getUserinfo()
            if ((usernameInput.text.toString()) in checkUsername) {
                Toast.makeText(this, "Username is already used", Toast.LENGTH_SHORT).show()
            } else {
                if (password1Input.text.toString() == password2Input.text.toString() && password1Input.text.toString() !== "") {
                    val db = DBHelper(this, null)//Connection to Database
                    db.insertLogin(usernameInput.text.toString(), password1Input.text.toString())//Add new User data to Database
                    Toast.makeText(this, "NEW User was added", Toast.LENGTH_SHORT).show()
                    onBackPressed()//go back after signing up
                } else {
                    Toast.makeText(this, "Passwords are not the same", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun getUserinfo(){

        val db = DBHelper(this, null)//Connection to Database

        val res= db.getData(db.table1Name)//get data from Database

        while(res!!.moveToNext()) {//Store data from Database
            val username = res.getString(1)
            checkUsername += username//store usernames to Array
        }
    }
}