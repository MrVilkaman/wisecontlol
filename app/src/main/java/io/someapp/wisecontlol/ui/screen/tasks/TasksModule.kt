package io.someapp.wisecontlol.ui.screen.tasks

import dagger.Module
import dagger.Provides
import io.someapp.wisecontlol.di.FragmentScope


@Module
class TasksModule {

    @FragmentScope
    @Provides
    fun provideScreenParam(fragment: TasksFragment): TasksScreenParam {
        val arguments = fragment.arguments
        return if (arguments?.containsKey(TasksScreen.KEY_CATEGORY_ID) == true) {
            TasksScreenParam(
                arguments.getLong(TasksScreen.KEY_CATEGORY_ID)
            )
        } else {
            TasksScreenParam()
        }
    }
}