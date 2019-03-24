package io.someapp.wisecontlol.ui.screen.main

import com.arellomobile.mvp.InjectViewState
import io.someapp.wisecontlol.BuildConfig
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.category.CategoryEntity
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.domain.CategoryInteractor
import io.someapp.wisecontlol.ui.core.BasePresenter
import io.someapp.wisecontlol.ui.screen.taskinfo.TaskInfoScreenEdit
import io.someapp.wisecontlol.ui.screen.tasks.TasksScreen
import kotlinx.coroutines.launch
import javax.inject.Inject


@InjectViewState
@FragmentScope
class MainPresenter @Inject constructor(
    private val categoryInteractor: CategoryInteractor
) : BasePresenter<MainView>() {
    fun onClickTasks() {
        router.navigateTo(TasksScreen())
    }

    fun onClickAddTask() {
        router.navigateTo(TaskInfoScreenEdit())
    }

    fun onClickCategory(value: CategoryEntity){
        router.navigateTo(TasksScreen(value))
    }

    fun onClickCategories() = launch{
        val list = categoryInteractor.getAllCategories()
        viewState.showCategoriesChooseDialog(list)
    }

    fun tryAddCategory(name: String) = launch {
        try {
            categoryInteractor.addCategory(name)
            viewState.showToast(R.string.category_added_done)
        } catch (e: Exception) {
            when (e) {
                is IllegalArgumentException -> viewState.showToast(R.string.category_exist)
                else -> {
                    if (BuildConfig.DEBUG) {
                        throw e
                    } else {
                        viewState.showToast(R.string.some_error)
                    }
                }
            }
        }

    }
}