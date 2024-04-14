package com.example.convidados

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteStatement
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar

class GuestForm : AppCompatActivity() {
    private lateinit var databaseApp: SQLiteDatabase
    private lateinit var edtGuestName: EditText
    private lateinit var btnSave: Button
    private var idReceivedParam: Int? = null

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)

        toolbar = findViewById(R.id.my_toolbarForm)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val intent: Intent = intent
        idReceivedParam = intent.getIntExtra("ID_SELECIONADO", 0)
        edtGuestName = findViewById(R.id.edtGuestName)
        load()

        btnSave = findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
            if (idReceivedParam!! > 0) {
                update()
            } else {
                save()
            }
        }
    }


    private fun update() {
        val valueName: String = edtGuestName!!.text.toString()
        try {
            databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
            val sql = "UPDATE guestTable SET name=? WHERE id=?"
            val stmt: SQLiteStatement = databaseApp.compileStatement(sql)
            stmt.bindString(1, valueName)
            stmt.bindLong(2, idReceivedParam!!.toLong())
            stmt.executeUpdateDelete()
            databaseApp.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        finish()
    }

    private fun load() {
        try {
            databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
            val cursor: Cursor = databaseApp.rawQuery("SELECT id, name from guestTable WHERE id= $idReceivedParam", null)
            cursor.moveToFirst()
            edtGuestName.setText(cursor.getString(1))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun save() {
        try {
            databaseApp = openOrCreateDatabase("dbGuestApp", MODE_PRIVATE, null)
            val sql = "INSERT INTO guestTable (name) values (?)"
            val stmt =  databaseApp.compileStatement(sql)
            stmt.bindString(1, edtGuestName.text.toString())
            stmt.executeInsert()
            databaseApp.close()
        } catch (e:Exception) {
            e.printStackTrace()
        }
        finish()
    }


}