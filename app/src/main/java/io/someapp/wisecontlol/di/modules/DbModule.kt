package io.someapp.wisecontlol.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.someapp.wisecontlol.data.db.WiseDatabase
import javax.inject.Singleton


@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDb(context: Context): WiseDatabase = Room.databaseBuilder(
        context,
        WiseDatabase::class.java, "database"
    )
//        .allowMainThreadQueries()
        .build()
}