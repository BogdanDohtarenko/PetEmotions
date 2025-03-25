package com.ideasapp.petemotions

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test


class ExampleUnitTest {

    private lateinit var numbers: IntArray
    private lateinit var expectedResults: IntArray

    @Before
    fun setup() {
        numbers = intArrayOf(6, 10, 15, 21, 9)
        expectedResults = intArrayOf(3, 5, 5, 7, 3)
    }

    @Test
    fun testFindMaxDivisorForMultipleNumbers() {
        for (i in numbers.indices) {
            val actualResult = findMaxDivisor(numbers[i])
            val expectedResult = expectedResults[i]
            assertEquals(
                "Failed for number: ${numbers[i]}, " +
                        "$expectedResult expected, " +
                        "but face with $actualResult",
                expectedResult,
                actualResult
            )
        }
    }

    private fun findMaxDivisor(num: Int): Int {
        for (i in num / 2 downTo 1) {
            if (num % i == 0) {
                return i
            }
        }
        return num
    }
}