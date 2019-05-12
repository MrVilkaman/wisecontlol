package io.someapp.wisecontlol.ui.utils

import java.util.*


class Date {
    companion object {
        fun todayNight(): java.util.Date {
            val instance = GregorianCalendar.getInstance()
            instance.set(Calendar.HOUR_OF_DAY, 0)
            instance.set(Calendar.MINUTE, 0)
            instance.set(Calendar.SECOND, 0)
            instance.set(Calendar.MILLISECOND, 0)
            instance.add(Calendar.DAY_OF_MONTH, 1)

            return instance.time
        }
    }
}


