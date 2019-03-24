package io.someapp.wisecontlol.ui.screen.tasks

import android.view.View
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
            val item = item
            val newState = item.task.isDone.not()
            itemView.checkbox.isChecked = newState
            taskPresenter.onCheckedTask(item.task.id, newState)
        }
    }

    override fun bind(item: TaskFullEntity, position: Int, payloads: Set<String>) {
        itemView.title.text = item.task.title
        itemView.checkbox.isChecked = item.task.isDone
        itemView.category.text = item.category?.title
        itemView.date.text = convertDate(item.task.startDate)
    }

    private val dateFormat = SimpleDateFormat("dd.MM HH:mm")
    private fun convertDate(startDate: Date?): String {
        startDate ?: return ""
        return dateFormat.format(startDate)
    }

}