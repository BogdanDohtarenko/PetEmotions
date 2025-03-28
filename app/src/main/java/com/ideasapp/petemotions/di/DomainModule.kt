package com.ideasapp.petemotions.di

import com.ideasapp.petemotions.data.repositories_impl.CalendarRepositoryImpl
import com.ideasapp.petemotions.data.repositories_impl.DayAttributesRepositoryImpl
import com.ideasapp.petemotions.data.repositories_impl.StatisticsRepositoryImpl
import com.ideasapp.petemotions.data.repositories_impl.TimetableRepositoryImpl
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import com.ideasapp.petemotions.domain.repositories.DayAttributesRepository
import com.ideasapp.petemotions.domain.repositories.StatisticsRepository
import com.ideasapp.petemotions.domain.repositories.TimetableRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Binds
    fun bindStatisticsRepository(impl:StatisticsRepositoryImpl): StatisticsRepository
    @Binds
    fun bindDayAttributesRepository(impl: DayAttributesRepositoryImpl): DayAttributesRepository
    @Binds
    fun bindCalendarRepository(impl: CalendarRepositoryImpl): CalendarRepository
    @Binds
    fun bindTimetableRepository(impl: TimetableRepositoryImpl): TimetableRepository
}