package io.someapp.wisecontlol.ui.screen.taskinfo

import com.arellomobile.mvp.InjectViewState
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.core.BasePresenter
import io.someapp.wisecontlol.ui.screen.tasks.TasksScreen
import javax.inject.Inject


@InjectViewState
@FragmentScope
class TaskInfoPresenter @Inject constructor(
) : BasePresenter<TaskInfoView>() {

}