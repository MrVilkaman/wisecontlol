package io.someapp.wisecontlol.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.someapp.wisecontlol.ui.container.App


class ReminderNotificationsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val appComponent = App.get(context).appComponent.notificationsSettingsManager()


        if (intent.action != null) {
            if (intent.action!!.equals(Intent.ACTION_BOOT_COMPLETED, ignoreCase = true)) {
                // Set the alarm here.
                appComponent.restartNotifications()
                return
            }
        }

        val intExtra = intent.getLongExtra("ЙЦУК", -1)
        if (intExtra == -1L) {
            return
        }
        appComponent.showMotivationNotification(intExtra)
        appComponent.restartNotifications()
    }
}