package com.ideasapp.petemotions.di

import com.ideasapp.petemotions.data.repositories_impl.CalendarRepositoryImpl
import com.ideasapp.petemotions.data.repositories_impl.TimetableRepositoryImpl
import com.ideasapp.petemotions.domain.repositories.CalendarRepository
import com.ideasapp.petemotions.domain.repositories.TimetableRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    @Binds
    fun bindCalendarRepository(impl: CalendarRepositoryImpl): CalendarRepository
    @Binds
    fun bindTimetableRepository(impl: TimetableRepositoryImpl): TimetableRepository
}