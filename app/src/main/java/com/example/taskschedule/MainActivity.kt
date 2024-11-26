package com.example.taskschedule

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var _taskName: MutableList<String>
    private lateinit var _taskDate: MutableList<String>
    private lateinit var _taskCategory: MutableList<String>
    private lateinit var _taskDescription: MutableList<String>
    private lateinit var _status: MutableList<Boolean>
    private lateinit var _visibility: MutableList<Boolean>
    private lateinit var _rvTasks: RecyclerView

    private var arTasks = arrayListOf<tasks>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // inisialisasi data
        var _btnAdd =
            findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btnAdd)
        var intentData = intent.getParcelableExtra<tasks>("kirimData", tasks::class.java)
        var pos = intent.getIntExtra("pos", 0)
        var intentData2 = intent.getParcelableExtra<tasks>("editData", tasks::class.java)
        _rvTasks = findViewById<RecyclerView>(R.id.rvTasks)

        // pindah ke page input tasks
        _btnAdd.setOnClickListener {
            val intent = Intent(this@MainActivity, InputTasks::class.java)
            startActivity(intent)
        }

        SiapkanData()
        // edit data
        if (intentData2 != null) {
            EditData(intentData2, pos)
        }

        // menambahkan data
        if (intentData != null) {
            _taskName.add(intentData.taskName)
            _taskDate.add(intentData.taskDate)
            _taskCategory.add(intentData.taskCategory)
            _taskDescription.add(intentData.taskDescription)
            _status.add(intentData.status)
            _visibility.add(intentData.visibility)
        }
        TambahData()
        TampilkanData()
    }

    fun SiapkanData() {
        _taskName = resources.getStringArray(R.array.taskName).toMutableList()
        _taskDate = resources.getStringArray(R.array.taskDate).toMutableList()
        _taskCategory = resources.getStringArray(R.array.taskCategory).toMutableList()
        _taskDescription = resources.getStringArray(R.array.taskDescription).toMutableList()
        _status = resources.getStringArray(R.array.status).map { it.toBoolean() }.toMutableList()
        _visibility =
            resources.getStringArray(R.array.visibility).map { it.toBoolean() }.toMutableList()
    }

    fun TambahData() {
        arTasks.clear()
        for (position in _taskName.indices) {
            val data = tasks(
                _taskName[position],
                _taskDate[position],
                _taskCategory[position],
                _taskDescription[position],
                _status[position],
                _visibility[position]
            )
            arTasks.add(data)
        }
    }

    fun EditData(data: tasks, pos: Int) {
        _taskName[pos] = data.taskName
        _taskDate[pos] = data.taskDate
        _taskCategory[pos] = data.taskCategory
        _taskDescription[pos] = data.taskDescription
        _status[pos] = data.status
        _visibility[pos] = data.visibility
    }

    fun TampilkanData() {
        _rvTasks.layoutManager = LinearLayoutManager(this)

        val adapterTasks = adapterRevView(arTasks)
        _rvTasks.adapter = adapterTasks

        adapterTasks.setOnItemClickCallBack(object : adapterRevView.OnItemClickCallBack {
            override fun onItemClicked(data: tasks, pos: Int) {
                val intent = Intent(this@MainActivity, InputTasks::class.java).apply {
                    putExtra("kirimDataEdit", data)
                    putExtra("pos", pos)
                }
                startActivity(intent)
            }

            override fun delData(pos: Int) {
                AlertDialog.Builder(this@MainActivity).setTitle("HAPUS DATA")
                    .setMessage("Apakah benar data " + _taskName[pos] + " akan dihapus?")
                    .setPositiveButton("HAPUS") { dialog, which ->
                        Toast.makeText(
                            this@MainActivity,
                            "Data " + _taskName[pos] + " berhasil dihapus",
                            Toast.LENGTH_LONG
                        ).show()
                        _taskName.removeAt(pos)
                        _taskDate.removeAt(pos)
                        _taskCategory.removeAt(pos)
                        _taskDescription.removeAt(pos)
                        _status.removeAt(pos)
                        _visibility.removeAt(pos)
                        TambahData()
                        TampilkanData()
                    }
                    .setNegativeButton("BATAL") { dialog, which ->
                        Toast.makeText(
                            this@MainActivity,
                            "Data " + _taskName[pos] + " tidak jadi dihapus",
                            Toast.LENGTH_LONG
                        ).show()
                    }.show()
            }
        })
    }
}