package io.someapp.wisecontlol.ui.screen.taskinfo

import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.di.SomeId


@Module
interface TaskInfoModule {
    @Binds
    @FragmentScope
    fun provide(frag: TaskInfoFragment): Fragment
}

@Module
interface TaskInfoEditModule {
    @Binds
    @FragmentScope
    fun provide(frag: TaskInfoFragmentEdit): Fragment
}


@Module
class IdModule {

    @FragmentScope
    @SomeId
    @Provides
    fun provideId(fragment: Fragment): Long? {
        val arguments = fragment.arguments
        return if (arguments?.containsKey(TaskInfoScreenEdit.KEY_ID) == true) {
            arguments.getLong(TaskInfoScreenEdit.KEY_ID)
        } else {
            null
        }
    }
    @FragmentScope
    @Provides
    fun provideEditMode(fragment: Fragment): Boolean {
        return fragment.arguments?.containsKey(TaskInfoScreenEdit.KEY_EDIT) == true
    }
}