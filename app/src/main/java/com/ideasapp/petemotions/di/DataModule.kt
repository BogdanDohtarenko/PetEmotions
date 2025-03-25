package com.ideasapp.petemotions.di

import android.content.Context
import com.ideasapp.petemotions.data.db.AppDatabase
import com.ideasapp.petemotions.data.db.dao.CalendarListDao
import com.ideasapp.petemotions.data.db.dao.DayAttributesDao
import com.ideasapp.petemotions.data.db.dao.TimetableDao
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
    fun provideDayAttributesDao(@ApplicationContext appContext: Context):DayAttributesDao {
        return AppDatabase.getInstance(appContext).DayAttributesDao()
    }

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