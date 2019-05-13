package io.someapp.wisecontlol.di

import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import io.someapp.wisecontlol.di.modules.AppModule
import io.someapp.wisecontlol.di.modules.DbModule
import io.someapp.wisecontlol.di.modules.NavigationModule
import io.someapp.wisecontlol.domain.NotificationsSettingsManager
import io.someapp.wisecontlol.ui.container.App
import io.someapp.wisecontlol.ui.navigation.AppNavigationModule
import io.someapp.wisecontlol.ui.navigation.WiseRouter
import javax.inject.Singleton


@Component(
    modules = [
        AppModule::class,
        DbModule::class,
        NavigationModule::class,
        AppNavigationModule::class,
        AndroidSupportInjectionModule::class
    ]
)
@Singleton
interface AppComponent {
    fun inject(app: App)

    fun getRouter(): WiseRouter

    fun notificationsSettingsManager(): NotificationsSettingsManager
}

