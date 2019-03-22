package io.someapp.wisecontlol.ui.navigation

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.ui.screen.main.MainFragment
import io.someapp.wisecontlol.ui.screen.taskinfo.TaskInfoFragment
import io.someapp.wisecontlol.ui.screen.taskinfo.TaskInfoModule
import io.someapp.wisecontlol.ui.screen.tasks.TasksFragment

@Module
interface AppMainScreenModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun provideMainFragment(): MainFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun provideTasksFragment(): TasksFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TaskInfoModule::class])
    fun provideTaskInfoFragment(): TaskInfoFragment
}