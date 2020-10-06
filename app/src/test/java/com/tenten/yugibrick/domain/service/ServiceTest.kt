package com.tenten.yugibrick.domain.service

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tenten.yugibrick.RxTestSchedulerRule
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

open class ServiceTest {

    fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    fun <T> uninitialized(): T = null as T

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mMockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val mRxRule = RxTestSchedulerRule()
}
