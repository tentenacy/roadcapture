package com.untilled.roadcapture

import com.untilled.roadcapture.utils.dateToSnsFormat
import com.untilled.roadcapture.utils.getFilterDate
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals("", dateToSnsFormat(LocalDateTime.of(2011,1,20,3,34,0)))
    }
}