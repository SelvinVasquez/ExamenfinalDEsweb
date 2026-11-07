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

data class Task(val id: String, val description: String, val created: Long, val completed: Boolean)

class TaskStore(context: Context) {
    private val prefs = context.getSharedPreferences("tasks", Context.MODE_PRIVATE)
    fun all(): List<Task> {
        val a = JSONArray(prefs.getString("items", "[]") ?: "[]")
        return (0 until a.length()).map {
            val o = a.getJSONObject(it)
            Task(o.getString("id"), o.getString("description"), o.getLong("created"), o.getBoolean("completed"))
        }
    }
    private fun save(tasks: List<Task>) {
        val a = JSONArray()
        tasks.forEach { t -> a.put(JSONObject().apply {
            put("id", t.id); put("description", t.description); put("created", t.created); put("completed", t.completed)
        }) }
        prefs.edit().putString("items", a.toString()).apply()
    }
    fun add(text: String) = save(all() + Task(UUID.randomUUID().toString(), text.trim(), System.currentTimeMillis(), false))
    fun complete(id: String, value: Boolean) = save(all().map { if (it.id == id) it.copy(completed = value) else it })
    fun delete(id: String) = save(all().filterNot { it.id == id })
}


