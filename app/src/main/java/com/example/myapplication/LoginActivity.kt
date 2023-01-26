package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class LoginActivity : AppCompatActivity() {

    private var checkUsername: Array<String> = arrayOf()//Array for every username
    private var checkPassword: Array<String> = arrayOf()//Array for every password
    private var iD: Array<String> = arrayOf()//Array for every user ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameInput = findViewById<EditText>(R.id.username)//Input field of username
        val passwordInput = findViewById<EditText>(R.id.password)//Input field of password
        val submitInput = findViewById<Button>(R.id.submit)//Submit Button
        val signUpInput = findViewById<Button>(R.id.signUp)//Sign up Button
        var passwordCheck = false

        submitInput.setOnClickListener {//what happens when Submit button is clicked
            passwordCheck = false
            getUserinfo()

           for (i in 1 until checkUsername.size){
               if (passwordInput.text.toString() == checkPassword.elementAt(i) && usernameInput.text.toString() == checkUsername.elementAt(i)){//check username and password
                   val intent = Intent(this,MainActivity::class.java)
                   intent.putExtra("UserID", iD.elementAt(i).toString())
                   intent.putExtra("Username", checkUsername.elementAt(i).toString())
                   startActivity(intent)//change Activity
                   usernameInput.setText("")
                   passwordInput.setText("")
                   passwordCheck = true
               }
           }
            if (!passwordCheck){
                Toast.makeText(this, "Wrong Username or Password", Toast.LENGTH_SHORT).show()
            }

       }
       signUpInput.setOnClickListener {//what happens when Sign Up button is clicked
           startActivity(Intent(this,SignUpActivity::class.java))
       }
    }

    private fun getUserinfo(){

        val db = DBHelper(this, null)//Connection to Database

        val res = db.getData(db.table1Name)//get data from Database

        while(res!!.moveToNext()) {//Store data from Database
            val id = res.getString(0)
            val username = res.getString(1)
            val password = res.getString(2)
            checkUsername += username//store usernames to Array
            checkPassword += password//store password to Array
            iD += id
        }
    }

}
