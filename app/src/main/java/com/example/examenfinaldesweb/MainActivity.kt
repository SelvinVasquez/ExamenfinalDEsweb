package com.example.examenfinaldesweb

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID
import java.util.Date
import java.text.DateFormat

class MainActivity : TaskActivity() {
    private lateinit var adapter: TaskAdapter
    private lateinit var summary: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = screen("Mis pendientes", "Organiza tu día, incluso sin conexión")
        summary = label(""); root.addView(summary)
        root.addView(button("+ Agregar tarea") { startActivity(Intent(this, AddTaskActivity::class.java)) })
        val list = ListView(this).apply { dividerHeight = dp(10) }
        adapter = TaskAdapter(); list.adapter = adapter
        val empty = label("No tienes tareas.\nPulsa Agregar tarea para comenzar.", 18)
        root.addView(empty); list.emptyView = empty
        root.addView(list, LinearLayout.LayoutParams(-1, 0, 1f))
        list.setOnItemClickListener { _, _, position, _ ->
            startActivity(Intent(this, TaskDetailActivity::class.java).putExtra("task_id", adapter.tasks[position].id))
        }
    }
    override fun onResume() { super.onResume(); refresh() }
    private fun refresh() {
        adapter.tasks = TaskStore(this).all(); adapter.notifyDataSetChanged()
        summary.text = "${adapter.tasks.count { !it.completed }} pendientes · ${adapter.tasks.count { it.completed }} completadas"
    }
    private inner class TaskAdapter : BaseAdapter() {
        var tasks = listOf<Task>()
        override fun getCount() = tasks.size
        override fun getItem(position: Int) = tasks[position]
        override fun getItemId(position: Int) = position.toLong()
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val task = tasks[position]
            return LinearLayout(this@MainActivity).apply {
                orientation = LinearLayout.HORIZONTAL
                descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
                setPadding(dp(8), dp(12), dp(8), dp(12))
                setBackgroundColor(if (task.completed) Color.rgb(226, 242, 233) else Color.WHITE)
                addView(CheckBox(this@MainActivity).apply {
                    isChecked = task.completed
                    contentDescription = "Completar: ${task.description}"
                    setOnCheckedChangeListener { _, value -> TaskStore(this@MainActivity).complete(task.id, value); refresh() }
                })
                addView(label("${task.description}\n${if (task.completed) "Completada" else "Pendiente"}", 17).apply {
                    if (task.completed) paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }, LinearLayout.LayoutParams(0, -2, 1f))
                addView(button("Eliminar") {
                    AlertDialog.Builder(this@MainActivity).setTitle("¿Eliminar tarea?").setMessage(task.description)
                        .setNegativeButton("Cancelar", null).setPositiveButton("Eliminar") { _, _ ->
                            TaskStore(this@MainActivity).delete(task.id); refresh()
                        }.show()
                })
            }
        }
    }
}


