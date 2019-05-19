package io.someapp.wisecontlol.ui.screen.taskinfo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.customListAdapter
import com.afollestad.materialdialogs.list.listItemsSingleChoice
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
import io.someapp.wisecontlol.domain.RememberUtils
import io.someapp.wisecontlol.ui.core.BaseFragment
import io.someapp.wisecontlol.ui.core.ItemListener
import io.someapp.wisecontlol.ui.core.withParam
import io.someapp.wisecontlol.ui.screen.main.CategoryAdapter
import io.someapp.wisecontlol.ui.utils.asString
import kotlinx.android.synthetic.main.screen_taskinfo_view_edit.*
import ru.terrakok.cicerone.android.support.SupportAppScreen
import java.text.SimpleDateFormat
import java.util.*


@FragmentScope
class TaskInfoFragmentEdit : BaseFragment<TaskInfoPresenter>(), TaskInfoView {

    @InjectPresenter
    override lateinit var presenter: TaskInfoPresenter

    @ProvidePresenter
    override fun providePresenter(): TaskInfoPresenter = super.providePresenter()

    override fun getLayoutId() = R.layout.screen_taskinfo_view_edit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        name_edit.setOnClickListener { presenter.showEditTitleDialog() }
        edit_start_date_dialog.setOnClickListener { showEditStareDateDialog() }
        remember_dialog.setOnClickListener { showEditRememberDialog() }
        task_save.setOnClickListener { presenter.save(task_content.asString()) }
        close.setOnClickListener { presenter.close() }

        category_selector.setOnClickListener {
            presenter.onClickCategories()
        }
    }

    private fun showEditStareDateDialog() {
        val min = Calendar.getInstance().apply { time = Date() }
        val current = presenter.getStart()?.let {
            Calendar.getInstance().apply { time = it }
        }
        MaterialDialog(context!!).show {
            dateTimePicker(
                minDateTime = min,
                currentDateTime = current,
                show24HoursView = true
            ) { dialog, dateTime -> presenter.updateStartDate(dateTime.time) }
            positiveButton(R.string.submit)
        }
    }


    private fun showEditRememberDialog() {

        val items = RememberUtils.rememberChooseVariants()
        val titls = items.map { it.text }

        MaterialDialog(context!!).show {
            listItemsSingleChoice(
                items = titls,
                waitForPositiveButton = false
            ) { dialog, index, text ->
                when (items.size) {
                    items.size - 1 -> showRememberTimeDialog()
                    else -> presenter.updateRemember(items[index])
                }
                dialog.dismiss()
            }
            negativeButton(R.string.delete) {
                presenter.clearRemember()
            }
        }
    }

    private fun showRememberTimeDialog() {
        val min = presenter.getStart()?.let {
            Calendar.getInstance().apply { time = it }
        }

        MaterialDialog(context!!).show {
            dateTimePicker(
                minDateTime = min,
                currentDateTime = min,
                show24HoursView = true
            ) { dialog, dateTime ->
                presenter.updateRemember(RememberUtils.getTimeItem(), dateTime.time)
            }
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

    override fun showEditTitleDialog(prefill: String) {
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

        if (task_content.text.isEmpty()) {
            task_content.setText(task.description)
        }

        category_selector.setText(currentTask.category?.title)

        val date: CharSequence = task.startDate?.let { dateFormat.format(it) } ?: ""
        textView2.text = getString(R.string.start_date, date)

        if (task.startDate == null) {
            remember_edit.visibility = View.GONE
            remember_dialog.visibility = View.GONE
            return
        }
        remember_edit.visibility = View.VISIBLE
        remember_dialog.visibility = View.VISIBLE

        val remembers = task.remembers
        if (remembers != null) {

            remember_edit.text = RememberUtils.getText(context!!, remembers)
        } else {
            remember_edit.setText(R.string.remember_null)
        }
    }

    override fun showTitleError() {
        showToast(R.string.task_title_request)
    }
}

open class TaskInfoScreenEdit(private val id: Long? = null) : SupportAppScreen() {
    companion object {
        const val KEY_ID = "KEY_ID"
        const val KEY_EDIT = "KEY_EDIT"
    }

    override fun getFragment() = createFragment().withParam {
        id?.let {
            putLong(KEY_ID, id)
        }
    }

    open fun createFragment(): Fragment = TaskInfoFragmentEdit().withParam {
        putBoolean(KEY_EDIT, true)
    }
}

@StateStrategyType(value = SkipStrategy::class)
interface TaskInfoView : MvpView {
    @StateStrategyType(value = AddToEndSingleStrategy::class)
    fun updateUi(currentTask: TaskFullEntity)

    fun showCategoriesChooseDialog(list: List<CategoryEntity>)

    fun showTitleError()
    @StateStrategyType(value = SkipStrategy::class)
    fun showEditTitleDialog(prefill: String)
}