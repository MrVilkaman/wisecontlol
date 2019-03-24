package io.someapp.wisecontlol.ui.screen.tasks

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.category.CategoryEntity
import io.someapp.wisecontlol.data.tasks.TaskEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.core.BaseFragment
import io.someapp.wisecontlol.ui.core.ItemListener
import io.someapp.wisecontlol.ui.core.withParam
import kotlinx.android.synthetic.main.screen_tasks_view.*
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject


@FragmentScope
class TasksFragment : BaseFragment<TasksPresenter>(), TasksView {

    @InjectPresenter
    override lateinit var presenter: TasksPresenter

    @ProvidePresenter
    override fun providePresenter(): TasksPresenter = super.providePresenter()

    override fun getLayoutId() = R.layout.screen_tasks_view

    @Inject
    lateinit var adapter: TasksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recycler_view.layoutManager = LinearLayoutManager(context!!)
        recycler_view.adapter = adapter

        adapter.onClick = object : ItemListener<TaskEntity> {
            override fun click(value: TaskEntity) {
                presenter.onClickTask(value)
            }
        }

        close.setOnClickListener {
            presenter.onBackPressed()
        }
    }

    override fun bindItems(list: List<TaskEntity>) {
        adapter.items = list
    }
}

class TasksScreen(private val categoryEntity: CategoryEntity? = null) : SupportAppScreen() {
    companion object {
        const val KEY_CATEGORY_ID = "KEY_CATEGORY_ID"
    }

    override fun getFragment() = TasksFragment().withParam {
        categoryEntity?.let { putLong(KEY_CATEGORY_ID, it.id) }
    }
}

interface TasksView : MvpView {
    fun bindItems(list: List<TaskEntity>)
}

data class TasksScreenParam(
    private val categoryId: Long? = null
)