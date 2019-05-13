package io.someapp.wisecontlol.domain

import android.content.Context
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.tasks.RememberEntity
import io.someapp.wisecontlol.data.tasks.TaskEntity
import io.someapp.wisecontlol.ui.screen.taskinfo.RememberChoose
import java.util.*
import java.util.concurrent.TimeUnit


object RememberUtils {
    private const val CHOOSE_TIME = 7

    private val items = listOf(
        RememberChoose(0, "Во время", 0),
        RememberChoose(1, "За 5 минут", 5),
        RememberChoose(2, "За 10 минут", 10),
        RememberChoose(3, "За 15 минут", 15),
        RememberChoose(4, "За 30 минут", 30),
        RememberChoose(5, "За 1 час", TimeUnit.HOURS.toMinutes(1)),
        RememberChoose(6, "За 1 день", TimeUnit.DAYS.toMinutes(1)),
        RememberChoose(CHOOSE_TIME, "Выбрать время", -1)
    )

    fun rememberChooseVariants() = items

    fun getById(id: Int) = items.first { it.id == id }
    fun getTimeItem() = getById(CHOOSE_TIME)

    fun getText(context: Context, remembers: RememberEntity): String {
        val byId = getById(remembers.id)

        return when (byId.id) {
            0 -> context.getString(
                R.string.remember, byId.text
            )
            CHOOSE_TIME -> Date(byId.minuted).toString()
            else -> context.getString(
                R.string.remember,
                context.getString(R.string.remember_temp, byId.text)
            )
        }

    }


    fun getRememberDate(task: TaskEntity): Date? {
        val rem = task.remembers ?: return null

        if (rem.id == CHOOSE_TIME) {
            return rem.date!!
        }

        val startDate = task.startDate ?: return null
        return Date(startDate.time + TimeUnit.MINUTES.toMillis(getById(rem.id).minuted))
    }
}