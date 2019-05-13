package io.someapp.wisecontlol.domain

import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.ReminderNotificationsReceiver
import io.someapp.wisecontlol.data.db.WiseDatabase
import io.someapp.wisecontlol.data.tasks.TaskEntity
import io.someapp.wisecontlol.ui.container.AppActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NotificationsSettingsManager @Inject constructor(
    private val context: Context,
    private val db: WiseDatabase
) {

    fun restartNotifications() {
        val intent = Intent(context, ReminderNotificationsReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, 0,
            intent, PendingIntent.FLAG_CANCEL_CURRENT
        )
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        am.cancel(pendingIntent)

        val current = Date()

        val taskDao = db.taskDao()


        GlobalScope.launch(Dispatchers.Main) {
            val nextNotificationTime: Pair<TaskEntity, Date>? = withContext(Dispatchers.IO) {
                taskDao.getAll()
                    .mapNotNull {
                        val second = RememberUtils.getRememberDate(it) ?: return@mapNotNull null
                        Pair(it, second)
                    }
                    .filter {
                        val after = it.second.after(current)
                        Log.d("qwe", "${it.second}.after($current) == $after")

                        after

                    }
                    .sortedBy { it.second.time }
                    .firstOrNull()
            }

            Log.d("qwe", "${nextNotificationTime?.first} ${nextNotificationTime?.second ?: "x"}")

            if (nextNotificationTime != null) {
                intent.putExtra("ЙЦУК", nextNotificationTime.first.id.toLong())
                am.setExact(AlarmManager.RTC_WAKEUP, nextNotificationTime.second.time, PendingIntent.getBroadcast(
                    context, 0,
                    intent, PendingIntent.FLAG_CANCEL_CURRENT
                )
                )
            }
        }
    }

    fun showMotivationNotification(intExtra: Long) {
        val viewIntent = Intent(context, AppActivity::class.java)
        viewIntent.putExtra("QWER", intExtra)
        viewIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        viewIntent.action = Intent.ACTION_MAIN
        val viewPendingIntent = PendingIntent.getActivity(
            context,
            0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, MOTIVATION_CHANNEL_ID)
            .setContentText("Зайди чтобы посмотреть!")
            .setContentTitle("Напоминаение!")
            .setDefaults(Notification.DEFAULT_LIGHTS)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentIntent(viewPendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }

    private val notificationManager by lazy { context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }


    fun createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel3 = NotificationChannel(
                MOTIVATION_CHANNEL_ID,
                "Пум пум",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel3.description = "Тыщь пыщь"
            notificationManager.createNotificationChannel(channel3)
        }
    }

    companion object {
        private const val MOTIVATION_CHANNEL_ID = "MOTIVATION_CHANNEL_ID"
    }
}