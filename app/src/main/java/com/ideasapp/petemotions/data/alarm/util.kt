package com.ideasapp.petemotions.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ideasapp.petemotions.domain.entity.timetable.TimetableItem


fun Context.cancelAlarm(timetableItem:TimetableItem) {
    val alarmManager = getAlarmManager()

    val intent = Intent(this, AlarmReceiver::class.java).apply {
        action = "ALARM_ACTION"
    }

    val pendingIntent = PendingIntent.getBroadcast(
        this,
        timetableItem.id,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
    )

    alarmManager?.cancel(pendingIntent)
}

fun Context.getAlarmManager() =
    getSystemService(Context.ALARM_SERVICE) as? AlarmManager


fun Context.setAlarm(timetableItem: TimetableItem) {
    val alarmManager = getAlarmManager()

    val intent = Intent(this, AlarmReceiver::class.java).apply {
        action = "ALARM_ACTION"
        putExtra("description", timetableItem.description)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        this,
        timetableItem.id,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
    )

    alarmManager?.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        timetableItem.dateTime,
        pendingIntent
    )
}

fun saveTasks(context: Context, tasks: List<TimetableItem>) {
    val sharedPreferences = context.getSharedPreferences("AlarmTasks", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    val tasksJson = Gson().toJson(tasks)
    editor.putString("tasks", tasksJson)
    editor.apply()
}

fun loadTasks(context: Context): List<TimetableItem> {
    val sharedPreferences = context.getSharedPreferences("AlarmTasks", Context.MODE_PRIVATE)
    val tasksJson = sharedPreferences.getString("tasks", null) ?: return emptyList()

    val type = object : TypeToken<List<TimetableItem>>() {}.type
    return Gson().fromJson(tasksJson, type)
}