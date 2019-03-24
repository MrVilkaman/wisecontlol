package io.someapp.wisecontlol.ui.screen.taskinfo

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.customListAdapter
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.category.CategoryEntity
import io.someapp.wisecontlol.data.tasks.TaskFullEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.core.BaseFragment
import io.someapp.wisecontlol.ui.core.ItemListener
import io.someapp.wisecontlol.ui.core.withParam
import io.someapp.wisecontlol.ui.screen.main.CategoryAdapter
import io.someapp.wisecontlol.ui.utils.asString
import kotlinx.android.synthetic.main.screen_taskinfo_view_edit.*
import ru.terrakok.cicerone.android.support.SupportAppScreen
import java.text.SimpleDateFormat


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

        category_selector.setOnClickListener {
            presenter.onClickCategories()
        }
    }

    private fun showEditStareDateDialog() {
        MaterialDialog(context!!).show {
            dateTimePicker { dialog, dateTime -> presenter.updateStartDate(dateTime.time) }
            positiveButton(R.string.submit)
        }
    }

    override fun showCategoriesChooseDialog(list: List<CategoryEntity>) {
        val adapter = CategoryAdapter(list)

        MaterialDialog(context!!).show {
            val materialDialog = this
            customListAdapter(adapter)
            positiveButton(R.string.close)

            adapter.onClick = object : ItemListener<CategoryEntity> {
                override fun click(value: CategoryEntity) {
                    presenter.onClickCategory(value)
                    materialDialog.dismiss()
                }
            }
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

    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
    override fun updateUi(currentTask: TaskFullEntity) {
        val task = currentTask.task
        task_title.text = task.title.ifBlank { getString(R.string.new_task_hint) }
        task_content.setText(task.description)
        category_selector.setText(currentTask.category?.title)

        val date: CharSequence = task.startDate?.let { dateFormat.format(it) } ?: ""
        textView2.text = getString(R.string.start_date, date)

    }

    override fun showTitleError() {
        showToast(R.string.task_title_request)
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

@StateStrategyType(value = SkipStrategy::class)
interface TaskInfoView : MvpView {
    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun updateUi(currentTask: TaskFullEntity)

    fun showCategoriesChooseDialog(list: List<CategoryEntity>)

    fun showTitleError()
}