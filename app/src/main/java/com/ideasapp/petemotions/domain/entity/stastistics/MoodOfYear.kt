package com.ideasapp.petemotions.domain.entity.stastistics

import java.time.Year

data class MoodOfYear(
    val year: Year,
    val januaryData: Int,
    val februaryData: Int,
    val marchData: Int,
    val aprilData: Int,
    val mayData: Int,
    val juneData: Int,
    val julyData: Int,
    val augustData: Int,
    val septemberData: Int,
    val octoberData: Int,
    val novemberData: Int,
    val decemberData: Int,
) {}