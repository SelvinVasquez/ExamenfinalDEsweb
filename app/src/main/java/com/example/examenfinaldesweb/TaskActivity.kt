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

open class TaskActivity : Activity() {
    fun dp(n: Int) = (n * resources.displayMetrics.density).toInt()
    fun label(value: String, size: Int = 16) = TextView(this).apply {
        text = value; textSize = size.toFloat(); setTextColor(Color.rgb(28, 46, 64))
        setPadding(0, dp(8), 0, dp(12))
    }
    fun button(value: String, action: () -> Unit) = Button(this).apply {
        text = value; isAllCaps = false; setOnClickListener { action() }
    }
    fun screen(title: String, subtitle: String): LinearLayout {
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(20), dp(16), dp(20), dp(16))
            setBackgroundColor(Color.rgb(244, 247, 251))
        }
        root.setOnApplyWindowInsetsListener { v, i ->
            v.setPadding(dp(20) + i.systemWindowInsetLeft, dp(16) + i.systemWindowInsetTop,
                dp(20) + i.systemWindowInsetRight, dp(16) + i.systemWindowInsetBottom)
            i
        }
        setContentView(root)
        root.addView(label(title, 30)); root.addView(label(subtitle))
        return root
    }
}


