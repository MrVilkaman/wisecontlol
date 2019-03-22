package io.someapp.wisecontlol.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import io.someapp.wisecontlol.data.WisePreference
import io.someapp.wisecontlol.data.WisePreferenceImpl
import io.someapp.wisecontlol.ui.utils.TextFormatter
import io.someapp.wisecontlol.ui.utils.TextFormatterImpl
import javax.inject.Singleton


@Module
class AppModule(
    private val context: Context
) {

    @Provides
    @Singleton
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun provideTextFormatter(): TextFormatter = TextFormatterImpl(context)

    @Provides
    @Singleton
    fun provideReglekPreference(): WisePreference = WisePreferenceImpl(context)
}