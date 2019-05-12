package io.someapp.wisecontlol.ui.screen.tasks

import dagger.Module
import dagger.Provides
import io.someapp.wisecontlol.di.FragmentScope
import java.util.*


@Module
class TasksModule {

    @FragmentScope
    @Provides
    fun provideScreenParam(fragment: TasksFragment): TasksScreenParam {
        val arguments = fragment.arguments ?: return TasksScreenParam()

        val categoryId = if (arguments.containsKey(TasksScreen.KEY_CATEGORY_ID)) {
            arguments.getLong(TasksScreen.KEY_CATEGORY_ID)
        } else {
            null
        }

        val date = if (arguments.containsKey(TasksScreen.KEY_DATE)) {
            Date(arguments.getLong(TasksScreen.KEY_DATE))
        } else {
            null
        }
        return TasksScreenParam(
            categoryId,
            date,
            arguments.containsKey(TasksScreen.KEY_WITHOUT_DATE)
        )
    }
}