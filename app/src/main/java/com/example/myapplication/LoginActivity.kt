package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat


class LoginActivity : AppCompatActivity() {

    private var checkUsername: Array<String> = arrayOf()//Array for every username
    private var checkPassword: Array<String> = arrayOf()//Array for every password
    private var iD: Array<String> = arrayOf()//Array for every user ID
    private var biometricPrompt: BiometricPrompt? = null
    private var promptInfo: PromptInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameInput = findViewById<EditText>(R.id.username)//Input field of username
        val passwordInput = findViewById<EditText>(R.id.password)//Input field of password
        val submitInput = findViewById<Button>(R.id.submit)//Submit Button
        val signUpInput = findViewById<Button>(R.id.signUp)//Sign up Button
        val VerificationButton = findViewById<ImageView>(R.id.bio_login)//Fingerprint Signe in
        var passwordCheck = false
        val a = this

        submitInput.setOnClickListener {//what happens when Submit button is clicked
            passwordCheck = false
            getUserinfo()

           for (i in checkUsername.indices){
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
        VerificationButton.setOnClickListener {
            buttonAuthenticate(a);
        }
        val executor = ContextCompat.getMainExecutor(this)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            biometricPrompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    println("Error")
                    Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_SHORT).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    println("Success")
                    Toast.makeText(this@LoginActivity, "Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(a,MainActivity::class.java)
                    intent.putExtra("UserID", 1.toString())
                    intent.putExtra("Username", 1.toString())
                    startActivity(intent)//change Activity
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    println("Failure")
                    Toast.makeText(this@LoginActivity, "Failure", Toast.LENGTH_SHORT).show()
                }
            })
        }

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Programmer World Authentication")
            .setNegativeButtonText("Cancel")
            .setConfirmationRequired(false)
            .build()

        val biometricManager: BiometricManager = BiometricManager.from(this)
        if (biometricManager.canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS) {
            println("Biometric Not Supported")
            return
        }

        biometricPrompt?.authenticate(promptInfo!!)

    }

    private fun getUserinfo(){

        val db = DBHelper(this, null)//Connection to Database

        val res = db.getData(db.table1Name)//get data from Database

        while(res!!.moveToNext()) {//Store data from Database
            val id = res.getString(0)
            val username = res.getString(1)
            val password = res.getString(2)
            if(!(username in checkUsername)) {
                checkUsername += username//store usernames to Array
            }
            if (!(password in checkPassword)){
                checkPassword += password//store password to Array
            }
            if (!(id in iD)){
                iD += id//store id to Array
            }

        }
    }
    fun buttonAuthenticate(view: LoginActivity) {
        val biometricManager: BiometricManager = BiometricManager.from(this)
        if (biometricManager.canAuthenticate() !== BiometricManager.BIOMETRIC_SUCCESS) {

            //TODO: Not for the Build
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            println("Biometric Not Supported")
            Toast.makeText(
                this@LoginActivity,
                "Biometric Not Supported",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (promptInfo != null) {
            biometricPrompt?.authenticate(promptInfo!!)
        }
    }

}
