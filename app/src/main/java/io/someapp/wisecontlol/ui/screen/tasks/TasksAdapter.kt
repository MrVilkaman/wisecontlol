package io.someapp.wisecontlol.ui.screen.tasks

import android.view.View
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.tasks.TaskEntity
import io.someapp.wisecontlol.ui.core.BaseVH
import io.someapp.wisecontlol.ui.core.SimpleBaseAdapter
import kotlinx.android.synthetic.main.layout_task_view.view.*
import javax.inject.Inject

class TasksAdapter @Inject constructor() : SimpleBaseAdapter<TaskEntity, TaskEntityVH>() {
    override fun getLayoutId() = R.layout.layout_task_view

    override fun getHolder(view: View) = TaskEntityVH(view)
}

class TaskEntityVH(view: View) : BaseVH<TaskEntity>(view) {
    override fun bind(item: TaskEntity, position: Int, payloads: Set<String>) {
        itemView.title.text = item.title
    }

}