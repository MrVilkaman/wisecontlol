package io.someapp.wisecontlol.ui.screen.smssender

import dagger.Module
import dagger.Provides
import io.someapp.wisecontlol.di.FragmentScope
import io.someapp.wisecontlol.di.SomeId


@Module
class SmsSenderModule {

    @FragmentScope
    @SomeId
    @Provides
    fun provideId(fragment: SmsSenderFragment): Long {
        return fragment.arguments?.getLong(SmsSenderScreen.KEY_ID) ?: throw IllegalArgumentException()
    }
}