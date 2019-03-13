package io.someapp.wisecontlol.ui.container

import android.app.Activity
import android.app.Application
import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.someapp.wisecontlol.di.AppComponent
import io.someapp.wisecontlol.di.DaggerAppComponent
import io.someapp.wisecontlol.di.modules.AppModule
import javax.inject.Inject


class App : Application(), HasActivityInjector {


    companion object {
        @JvmStatic
        fun get(context: Context): App = context.applicationContext as App
    }

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector

    private lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
        appComponent
            .inject(this)
    }


}