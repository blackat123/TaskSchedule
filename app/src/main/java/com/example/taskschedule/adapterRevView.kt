package com.example.taskschedule

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterRevView(private val listTasks: ArrayList<tasks>) :
    RecyclerView.Adapter<adapterRevView.ListViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    interface OnItemClickCallBack {
        fun onItemClicked(data: tasks, pos: Int)
        fun delData(pos: Int)
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _taskName: TextView = itemView.findViewById(R.id.tvTaskName)
        var _taskDate: TextView = itemView.findViewById(R.id.tvDate)
        var _taskCategory: TextView = itemView.findViewById(R.id.tvCategory)
        var _taskDescription: TextView = itemView.findViewById(R.id.tvDescription)
        var _btnDelete: Button = itemView.findViewById(R.id.btnDelete)
        var _btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        var _btnDone: Button = itemView.findViewById(R.id.btnDone)
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int
    ) {
        var task = listTasks[position]

        holder._taskName.setText(task.taskName)
        holder._taskDate.setText(task.taskDate)
        holder._taskCategory.setText(task.taskCategory)
        holder._taskDescription.setText(task.taskDescription)
        holder._btnDelete.setOnClickListener {
            onItemClickCallBack.delData(position)
        }
        holder._btnEdit.setOnClickListener {
            onItemClickCallBack.onItemClicked(task, position)
        }
        holder._btnDone.setOnClickListener {
            if (holder._btnDone.text == "Start") {
                holder._btnDone.setText("Finish")
                holder._btnEdit.isEnabled = false
                holder._btnDone.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#07ad2e"))

            } else {
                holder._btnDone.setText("Done")
                holder._btnDone.isEnabled = false
                holder._btnDone.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#636363"))
            }
        }
    }

    override fun getItemCount(): Int {
        return listTasks.size
    }
}