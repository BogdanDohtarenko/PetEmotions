package com.ideasapp.petemotions.di

import android.app.Application
import android.content.Context
import com.ideasapp.petemotions.data.db.AppDatabase
import com.ideasapp.petemotions.data.db.CalendarListDao
import com.ideasapp.petemotions.data.db.TimetableDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Singleton
    @Provides
    fun provideCalendarDao(@ApplicationContext appContext: Context): CalendarListDao {
        return AppDatabase.getInstance(appContext).CalendarListDao()
    }

    @Singleton
    @Provides
    fun provideTimetableDao(@ApplicationContext appContext: Context): TimetableDao {
        return AppDatabase.getInstance(appContext).TimetableDao()
    }
}