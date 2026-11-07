package com.example.examenfinaldesweb

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class TaskFlowTest {
    @Test fun createViewCompletePersistAndDelete() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val context = instrumentation.targetContext
        context.getSharedPreferences("tasks", 0).edit().clear().commit()
        val activity = instrumentation.startActivitySync(Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        try {
            onView(withText("+ Agregar tarea")).perform(click())
            onView(withText("Guardar tarea")).perform(click())
            onView(withId(R.id.task_description)).check(matches(hasErrorText("Escribe una descripción")))
            onView(withId(R.id.task_description)).perform(typeText("Preparar informe"), closeSoftKeyboard())
            onView(withText("Guardar tarea")).perform(click())
            onView(withText("Preparar informe\nPendiente")).perform(click())
            onView(withText("Detalle de tarea")).check(matches(isDisplayed()))
            onView(withText("Pendiente")).perform(click())
            onView(withText("Volver a la lista")).perform(click())
            onView(withText("Preparar informe\nCompletada")).check(matches(isDisplayed()))
            val saved = TaskStore(context).all().single()
            assertTrue(saved.completed)
            assertTrue(saved.created > 0)
            assertEquals("Preparar informe", saved.description)
            onView(withText("Eliminar")).perform(click())
            onView(withText("Eliminar")).perform(click())
            assertTrue(TaskStore(context).all().isEmpty())
        } finally { activity.finish() }
    }
}

