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

class AddTaskActivity : TaskActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = screen("Nueva tarea", "Escribe el pendiente que necesitas recordar")
        val input = EditText(this).apply {
            id = R.id.task_description; hint = "Descripción de la tarea"; minLines = 3; maxLines = 8
            gravity = android.view.Gravity.TOP
            inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE or android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
            filters = arrayOf(android.text.InputFilter.LengthFilter(1000))
        }
        root.addView(input)
        root.addView(button("Guardar tarea") {
            val text = input.text.toString().trim()
            if (text.isEmpty()) { input.error = "Escribe una descripción"; input.requestFocus() }
            else { TaskStore(this).add(text); Toast.makeText(this, "Tarea guardada", Toast.LENGTH_SHORT).show(); finish() }
        })
        root.addView(button("Cancelar y volver") { finish() })
    }
}



