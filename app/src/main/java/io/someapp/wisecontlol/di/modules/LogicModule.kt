package io.someapp.wisecontlol.di.modules

import dagger.Binds
import dagger.Module
import io.someapp.wisecontlol.di.UIScope
import io.someapp.wisecontlol.domain.CategoryInteractor
import io.someapp.wisecontlol.domain.CategoryInteractorImpl
import io.someapp.wisecontlol.domain.TaskInteractor
import io.someapp.wisecontlol.domain.TaskInteractorImpl


@Module
interface LogicModule {

    @Binds
    @UIScope
    fun provide(impl: CategoryInteractorImpl): CategoryInteractor

    @Binds
    @UIScope
    fun provideTaskInteractor(impl: TaskInteractorImpl): TaskInteractor
}