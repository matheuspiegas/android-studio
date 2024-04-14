package com.example.convidados

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {

    lateinit var btnLogin: Button
    lateinit var btnSignUp: TextView
    lateinit var databaseApp: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        createDatabase()
        btnLogin = findViewById(R.id.btnLogin)
        val emailText = findViewById<EditText>(R.id.edtEmailLogin)
        val passwordText = findViewById<EditText>(R.id.edtPasswordLogin)
        btnLogin.setOnClickListener {
            var i = Intent(this, MainActivity::class.java)
            var exists = checkData(emailText.text.toString(), passwordText.text.toString(), i)
            if(exists) {
                startActivity(i)
            }
            if(!exists) {
                if(emailText.text.isEmpty() && passwordText.text.isEmpty()){
                    Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Email ou senha inválidos", Toast.LENGTH_SHORT).show()
                }
            }

        }

        btnSignUp = findViewById(R.id.txtSignUp)
        btnSignUp.setOnClickListener {
            var i = Intent(this, SignUp::class.java)
            startActivity(i)
        }
    }

    private fun checkData(email: String, password: String, i: Intent): Boolean {
        try {
            databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
            var cursor = databaseApp.rawQuery(
                "SELECT email, password FROM usersTable WHERE email = ? AND password = ? ", arrayOf(email, password)
            )
            var exists = cursor.moveToFirst()
            databaseApp.close()
            return exists
        } catch (e: Exception) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun createDatabase() {
        try {
            databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
            databaseApp.execSQL(
                "CREATE TABLE IF NOT EXISTS usersTable" +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, email VARCHAR, password VARCHAR)"
            )
            databaseApp.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}