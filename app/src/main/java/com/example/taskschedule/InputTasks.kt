package com.example.taskschedule

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InputTasks : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_input_tasks)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // inisialisasi data
        var _etTaskName = findViewById<EditText>(R.id.etTaskName)
        var _etTaskDate = findViewById<EditText>(R.id.etDate)
        var _etTaskCategory = findViewById<EditText>(R.id.etCategory)
        var _etTaskDescription = findViewById<EditText>(R.id.etDescription)
        var _btnSave = findViewById<Button>(R.id.btnSave)
        var intentData = intent.getParcelableExtra<tasks>("kirimDataEdit", tasks::class.java)
        var pos = intent.getIntExtra("pos", 0)

        // save data
        if (intentData != null) {
            _etTaskName.setText(intentData.taskName)
            _etTaskDate.setText(intentData.taskDate)
            _etTaskCategory.setText(intentData.taskCategory)
            _etTaskDescription.setText(intentData.taskDescription)

            _btnSave.setOnClickListener {
                val taskName = _etTaskName.text.toString()
                val taskDate = _etTaskDate.text.toString()
                val taskCategory = _etTaskCategory.text.toString()
                val taskDescription = _etTaskDescription.text.toString()
                val status = false
                val visibility = false

                val dataKirim =
                    tasks(taskName, taskDate, taskCategory, taskDescription, status, visibility)

                val intent = Intent(this@InputTasks, MainActivity::class.java).apply {
                    putExtra("editData", dataKirim)
                    putExtra("pos", pos)
                }
                startActivity(intent)
                finish()
            }
        } else {
            _btnSave.setOnClickListener {
                val taskName = _etTaskName.text.toString()
                val taskDate = _etTaskDate.text.toString()
                val taskCategory = _etTaskCategory.text.toString()
                val taskDescription = _etTaskDescription.text.toString()
                val status = false
                val visibility = false

                val dataKirim =
                    tasks(taskName, taskDate, taskCategory, taskDescription, status, visibility)

                val intent = Intent(this@InputTasks, MainActivity::class.java).apply {
                    putExtra("kirimData", dataKirim)
                }
                startActivity(intent)
                finish()
            }
        }
    }
}