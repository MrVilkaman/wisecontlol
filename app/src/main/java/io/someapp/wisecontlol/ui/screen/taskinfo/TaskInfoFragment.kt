package io.someapp.wisecontlol.ui.screen.taskinfo

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.tasks.TaskEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.core.BaseFragment
import io.someapp.wisecontlol.ui.core.withParam
import io.someapp.wisecontlol.ui.utils.datedialog.dateTimePicker
import kotlinx.android.synthetic.main.screen_taskinfo_view_edit.*
import io.someapp.wisecontlol.ui.utils.asString
import ru.terrakok.cicerone.android.support.SupportAppScreen


@FragmentScope
class TaskInfoFragment : BaseFragment<TaskInfoPresenter>(), TaskInfoView {

    @InjectPresenter
    override lateinit var presenter: TaskInfoPresenter

    @ProvidePresenter
    override fun providePresenter(): TaskInfoPresenter = super.providePresenter()

    override fun getLayoutId() = R.layout.screen_taskinfo_view_edit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        name_edit.setOnClickListener { showEditTitleDialog() }
        edit_start_date_dialog.setOnClickListener { showEditStareDateDialog() }
        task_save.setOnClickListener { presenter.save(task_content.asString()) }
        close.setOnClickListener { presenter.close() }
    }

    private fun showEditStareDateDialog() {
        MaterialDialog(context!!).show {
            dateTimePicker { dialog, dateTime ->  }
            positiveButton(R.string.submit)
        }

    }

    private fun showEditTitleDialog() {

        val prefill = task_title.text
        MaterialDialog(context!!).show {
            input(hintRes = R.string.edit_title_hint, prefill = prefill) { _, text ->
                presenter.updateTitle(text.toString())
            }
            positiveButton(R.string.submit)
        }
    }

    override fun updateUi(currentTask: TaskEntity) {
        task_title.text = currentTask.title
        task_content.setText(currentTask.description)
    }
}

class TaskInfoScreen(private val id: Long? = null) : SupportAppScreen() {
    companion object {
        const val KEY_ID = "KEY_ID"
    }

    override fun getFragment() = TaskInfoFragment().withParam {
        id?.let {
            putLong(KEY_ID, id)
        }
    }
}

interface TaskInfoView : MvpView {
    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun updateUi(currentTask: TaskEntity)
}