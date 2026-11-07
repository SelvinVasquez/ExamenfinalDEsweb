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

class TaskDetailActivity : TaskActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra("task_id") ?: run { finish(); return }
        val store = TaskStore(this)
        val task = store.all().find { it.id == id } ?: run { finish(); return }
        val root = screen("Detalle de tarea", "Toda la información de tu pendiente")
        val scroll = ScrollView(this)
        val content = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        content.addView(label(task.description, 23))
        content.addView(label("Fecha de creación\n${DateFormat.getDateTimeInstance().format(Date(task.created))}"))
        content.addView(CheckBox(this).apply {
            isChecked = task.completed; text = if (task.completed) "Completada" else "Pendiente"
            setOnCheckedChangeListener { _, value -> store.complete(id, value); text = if (value) "Completada" else "Pendiente" }
        })
        scroll.addView(content); root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))
        root.addView(button("Volver a la lista") { finish() })
    }
}

