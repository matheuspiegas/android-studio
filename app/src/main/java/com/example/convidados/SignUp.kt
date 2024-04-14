package com.example.convidados

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class SignUp : AppCompatActivity() {
    lateinit var btnRegister: Button
    lateinit var edit_name: EditText
    lateinit var edit_email: EditText
    lateinit var edit_password: EditText
    lateinit var toolbar: Toolbar
    lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnRegister = findViewById(R.id.btnRegister)
        edit_email = findViewById(R.id.edit_email)
        edit_name = findViewById(R.id.edit_name)
        edit_password = findViewById(R.id.edit_password)



        toolbar = findViewById(R.id.my_toolbarSignUp)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {
            signUser(edit_name.text.toString(), edit_password.text.toString(), edit_email.text.toString())
        }
    }

    private fun signUser (name: String, password: String, email: String) {
        try {
            if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            } else {
                database = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
                var sql = "INSERT INTO usersTable(name, password, email) VALUES(?, ?, ?)"
                var stmt = database.compileStatement(sql)
                stmt.bindString(1, name)
                stmt.bindString(2, password)
                stmt.bindString(3, email)
                stmt.executeInsert()
                database.close()

                var i = Intent(this, LoginActivity::class.java)
                Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT ).show()
                startActivity(i)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}