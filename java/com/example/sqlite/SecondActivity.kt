package com.example.sqlite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class SecondActivity : AppCompatActivity() {
    private val db = DBHelper(this, null)
    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var positionSpinner: Spinner
    private lateinit var addNameButton: Button
    private lateinit var printNameButton: Button
    private lateinit var clearTableButton: Button
    private lateinit var nameTextView: TextView
    private lateinit var ageTextView: TextView
    private lateinit var roleTV: TextView
    private lateinit var lastNameTV: TextView
    private lateinit var lastNameET: EditText

    private val position = arrayOf("Разработчик", "Менеджер", "Дизайнер")

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_second)

        init()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, position)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        positionSpinner.adapter = adapter

        addNameButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val lastname = lastNameET.text.toString()
            val age = ageEditText.text.toString()
            val role = positionSpinner.selectedItem.toString()
            db.addName(name, lastname, age, role)
            Toast.makeText(this, "$lastname $name $age $role: добавлен в базу данных", Toast.LENGTH_SHORT).show()
        }

        printNameButton.setOnClickListener {
            val cursor = db.getInfo()
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        nameTextView.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)) + "\n")
                        lastNameTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_LAST_NAME)) + "\n")
                        ageTextView.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_AGE)) + "\n")
                        roleTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ROLE)) + "\n")
                    } while (cursor.moveToNext())
                }
                cursor.close()
            }
        }

        clearTableButton.setOnClickListener {
            db.removeAll()
            nameTextView.text = ""
            lastNameTV.text = ""
            ageTextView.text = ""
            roleTV.text = ""
        }
    }

    private fun init() {
        lastNameTV = findViewById(R.id.lastNameTV)
        lastNameET = findViewById(R.id.lastNameET)
        nameEditText = findViewById(R.id.enterNameET)
        ageEditText = findViewById(R.id.enterAgeET)
        positionSpinner = findViewById(R.id.positionSpinner)
        addNameButton = findViewById(R.id.addNameBTN)
        printNameButton = findViewById(R.id.printNameBTN)
        clearTableButton = findViewById(R.id.clearTableBTN)
        nameTextView = findViewById(R.id.nameTV)
        ageTextView = findViewById(R.id.ageTV)
        roleTV = findViewById(R.id.roleTV)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.second_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}