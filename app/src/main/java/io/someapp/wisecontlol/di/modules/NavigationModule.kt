package io.someapp.wisecontlol.di.modules

import dagger.Module
import dagger.Provides
import io.someapp.wisecontlol.ui.navigation.WiseRouter
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Singleton


@Module
class NavigationModule {

    private val cicerone = Cicerone.create(WiseRouter())

    @Provides
    @Singleton
    fun getNavigatorHolder(): NavigatorHolder = cicerone.navigatorHolder

    @Provides
    @Singleton
    fun getRouter(): WiseRouter = cicerone.router
}