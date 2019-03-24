package io.someapp.wisecontlol.ui.navigation

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.screen.main.MainFragment
import io.someapp.wisecontlol.ui.screen.taskinfo.*
import io.someapp.wisecontlol.ui.screen.tasks.TasksFragment
import io.someapp.wisecontlol.ui.screen.tasks.TasksModule

@Module
interface AppMainScreenModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun provideMainFragment(): MainFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TasksModule::class])
    fun provideTasksFragment(): TasksFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TaskInfoEditModule::class, IdModule::class])
    fun provideTaskInfoEditFragment(): TaskInfoFragmentEdit

    @FragmentScope
    @ContributesAndroidInjector(modules = [TaskInfoModule::class, IdModule::class])
    fun provideTaskInfoFragment(): TaskInfoFragment
}