package io.someapp.wisecontlol.ui.navigation

import dagger.Module
import dagger.android.ContributesAndroidInjector
import io.someapp.wisecontlol.di.UIScope
import io.someapp.wisecontlol.ui.container.AppActivity

@Module
interface AppNavigationModule {

    @UIScope
    @ContributesAndroidInjector(modules = [AppMainScreenModule::class])
    fun provideAppActivity(): AppActivity

}

