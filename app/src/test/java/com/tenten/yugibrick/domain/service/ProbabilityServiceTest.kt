package com.tenten.yugibrick.domain.service

import com.tenten.yugibrick.domain.model.Card
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.InjectMocks

/**
 * Created by Exequiel Egbert V. Ponce on 10/6/2020.
 */
class ProbabilityServiceTest : ServiceTest() {

    @InjectMocks
    lateinit var service: ProbabilityService

    @Test
    fun `should return same value if combo pieces are shuffled`() {
        val combo1 = service.comboCalculator(
            listOf(
                Card("card 1", 3),
                Card("card 2", 30)
            ),
            5,
            40
        )

        val combo2 = service.comboCalculator(
            listOf(
                Card("card 2", 30),
                Card("card 1", 3)
            ),
            5,
            40
        )

        assertEquals(combo1, combo2)
    }

    @Test
    fun test() {
        val combo1 = service.comboCalculator(
            listOf(
                Card("card 1", 3)
            ),
            5,
            40
        )

        println("combo: $combo1")
    }
}