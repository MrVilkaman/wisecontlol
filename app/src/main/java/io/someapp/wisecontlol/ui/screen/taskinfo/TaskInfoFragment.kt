package io.someapp.wisecontlol.ui.screen.taskinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.category.CategoryEntity
import io.someapp.wisecontlol.data.tasks.TaskFullEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.domain.RememberUtils
import io.someapp.wisecontlol.ui.core.BaseFragment
import kotlinx.android.synthetic.main.screen_taskinfo_view.*
import java.text.SimpleDateFormat


@FragmentScope
class TaskInfoFragment : BaseFragment<TaskInfoPresenter>(), TaskInfoView {

    @InjectPresenter
    override lateinit var presenter: TaskInfoPresenter

    @ProvidePresenter
    override fun providePresenter(): TaskInfoPresenter = super.providePresenter()

    override fun getLayoutId() = R.layout.screen_taskinfo_view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        close.setOnClickListener { presenter.close() }
        edit.setOnClickListener { presenter.onClickEdit() }
        delete.setOnClickListener { presenter.onClickDelete() }
        send.setOnClickListener { presenter.onClickSend() }
    }

    override fun showCategoriesChooseDialog(list: List<CategoryEntity>) {
        //не используется
    }

    private fun showEditTitleDialog() {
        //не используется
    }

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")

    override fun updateUi(currentTask: TaskFullEntity) {
        val task = currentTask.task
        task_title.text = task.title.ifBlank { getString(R.string.new_task_hint) }
        task_content.text = task.description.ifBlank { getString(R.string.task_content_hint) }
        val title = currentTask.category?.title
        category.text = "${getString(R.string.category)} $title"

        val date = task.startDate?.let { dateFormat.format(it) }
        if (date != null) {
            textView2.visibility = View.VISIBLE
            textView2.text = getString(R.string.start_date, date)
        } else {
            textView2.visibility = View.GONE
        }
        val remembers = task.remembers
        if (remembers != null) {
            remember.text = RememberUtils.getText(context!!, remembers)
        } else {
            remember.text = null
        }
    }

    override fun showTitleError() {
        showToast(R.string.task_title_request)
    }

    override fun showEditTitleDialog(prefill: String) {
        //ignore
    }
}

class TaskInfoScreen(id: Long) : TaskInfoScreenEdit(id) {
    override fun createFragment(): Fragment {
        return TaskInfoFragment()
    }
}

