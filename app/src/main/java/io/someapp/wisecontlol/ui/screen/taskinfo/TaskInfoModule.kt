package io.someapp.wisecontlol.ui.screen.taskinfo

import dagger.Module
import dagger.Provides
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.di.SomeId


@Module
class TaskInfoModule {

    @FragmentScope
    @SomeId
    @Provides
    fun provideId(fragment: TaskInfoFragment): Long? {
        val arguments = fragment.arguments
        return if (arguments?.containsKey(TaskInfoScreen.KEY_ID) == true) {
            arguments.getLong(TaskInfoScreen.KEY_ID)
        } else {
            null
        }
    }
}