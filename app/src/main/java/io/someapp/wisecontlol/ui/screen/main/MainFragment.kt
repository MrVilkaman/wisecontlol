package io.someapp.wisecontlol.ui.screen.main

import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.afollestad.materialdialogs.list.customListAdapter
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.category.CategoryEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.core.BaseFragment
import io.someapp.wisecontlol.ui.core.ItemListener
import kotlinx.android.synthetic.main.screen_main_view.*
import ru.terrakok.cicerone.android.support.SupportAppScreen


@FragmentScope
class MainFragment : BaseFragment<MainPresenter>(), MainView {

    @InjectPresenter
    override lateinit var presenter: MainPresenter

    @ProvidePresenter
    override fun providePresenter(): MainPresenter = super.providePresenter()

    override fun getLayoutId() = R.layout.screen_main_view

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tasks.setOnClickListener {
            presenter.onClickTasks()
        }
        add_task.setOnClickListener {
            presenter.onClickAddTask()
        }

        categories.setOnClickListener {
            presenter.onClickCategories()
        }
        add_category.setOnClickListener {
            showAddCategoryDialog()
        }
    }

    private fun showAddCategoryDialog() {
        MaterialDialog(context!!).show {
            input(hintRes = R.string.added_category_hint, allowEmpty = false) { _, text ->
                presenter.tryAddCategory(text.toString())
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
}

object MainScreen : SupportAppScreen() {
    override fun getFragment() = MainFragment()
}

@StateStrategyType(value = SkipStrategy::class)
interface MainView : MvpView {

    fun showToast(textId: Int)

    fun showCategoriesChooseDialog(list: List<CategoryEntity>)
}