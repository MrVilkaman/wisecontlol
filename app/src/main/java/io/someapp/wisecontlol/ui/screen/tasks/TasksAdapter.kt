package io.someapp.wisecontlol.ui.screen.tasks

import android.view.View
import androidx.core.content.ContextCompat
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.tasks.TaskFullEntity
import io.someapp.wisecontlol.ui.core.BaseVH
import io.someapp.wisecontlol.ui.core.SimpleBaseAdapter
import kotlinx.android.synthetic.main.layout_task_view.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class TasksAdapter @Inject constructor(
    private val taskPresenter: TasksPresenter

) : SimpleBaseAdapter<TaskFullEntity, TaskEntityVH>() {
    override fun getLayoutId() = R.layout.layout_task_view

    override fun getHolder(view: View) = TaskEntityVH(view, taskPresenter)
}

class TaskEntityVH(view: View, private val taskPresenter: TasksPresenter) : BaseVH<TaskFullEntity>(view) {

    init {
        itemView.checkbox.setOnClickListener {
            val newState = itemView.checkbox.isChecked
            itemView.checkbox.isChecked = newState
            taskPresenter.onCheckedTask(this.item.task.id, newState)
        }
    }

    override fun bind(item: TaskFullEntity, position: Int, payloads: Set<String>) {
        itemView.title.text = item.task.title
        val done = item.task.isDone
        itemView.checkbox.isChecked = done
        itemView.category.text = item.category?.title
        val startDate = item.task.startDate
        if (taskPresenter.beforeDate(startDate) && done.not()) {
            val color = ContextCompat.getColor(itemView.context, R.color.color_red)
            itemView.title.setTextColor(color)
            itemView.date.setTextColor(color)
        } else {
            val color = ContextCompat.getColor(itemView.context, R.color.color_black)
            itemView.title.setTextColor(color)
            itemView.date.setTextColor(color)
        }

        itemView.date.text = convertDate(startDate)
    }

    private val dateFormat = SimpleDateFormat("dd.MM HH:mm")
    private fun convertDate(startDate: Date?): String {
        startDate ?: return ""
        return dateFormat.format(startDate)
    }

}