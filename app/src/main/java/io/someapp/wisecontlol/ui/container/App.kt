package io.someapp.wisecontlol.ui.container

import android.app.Activity
import android.app.Application
import android.content.Context
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.someapp.wisecontlol.R
import io.someapp.wisecontlol.data.category.CategoryEntity
import io.someapp.wisecontlol.data.db.WiseDatabase
import io.someapp.wisecontlol.data.db.category.CategoryDao
import io.someapp.wisecontlol.di.AppComponent
import io.someapp.wisecontlol.di.DaggerAppComponent
import io.someapp.wisecontlol.di.modules.AppModule
import io.someapp.wisecontlol.ui.utils.withIO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class App : Application(), HasActivityInjector {


    companion object {
        @JvmStatic
        fun get(context: Context): App = context.applicationContext as App
    }

    @Inject
    lateinit var db: WiseDatabase
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


        initDB()
    }

    private fun initDB() {
        GlobalScope.launch {
            withIO {
                val categoryDao = db.categoryDao()
                if (categoryDao.getById(CategoryDao.EMPTY_CATEGORY) == null) {
                    categoryDao.insert(CategoryEntity().apply {
                        id = CategoryDao.EMPTY_CATEGORY
                        title = getString(R.string.empty_category)
                    })
                }

            }
        }
    }


}