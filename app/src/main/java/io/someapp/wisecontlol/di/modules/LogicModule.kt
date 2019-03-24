package io.someapp.wisecontlol.di.modules

import dagger.Binds
import dagger.Module
import io.someapp.wisecontlol.di.UIScope
import io.someapp.wisecontlol.domain.CategoryInteractor
import io.someapp.wisecontlol.domain.CategoryInteractorImpl


@Module
interface LogicModule {

    @Binds
    @UIScope
    fun provide(impl: CategoryInteractorImpl): CategoryInteractor
}