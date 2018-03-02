package com.example.service

import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.Appender
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.Logger
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner
import java.lang.StringBuilder

@RunWith(MockitoJUnitRunner::class)
class ApiServiceTest {

    @InjectMocks
    var service: ApiService = ApiService()

    @Mock
    private val mockAppender: Appender? = null

    @Captor
    private val captorLoggingEvent: ArgumentCaptor<LogEvent>? = null

    private var logger1: Logger? = null
    private var logger2: Logger? = null

    @Before
    fun setup() {
        `when`(mockAppender!!.name).thenReturn("MockAppender")
        `when`(mockAppender.isStarted).thenReturn(true)
        `when`(mockAppender.isStopped).thenReturn(false)

        logger1 = LogManager.getLogger("logger1") as Logger
        logger1!!.addAppender(mockAppender)
        logger1!!.level = Level.INFO

        logger2 = LogManager.getLogger("logger2") as Logger
        logger2!!.addAppender(mockAppender)
        logger2!!.level = Level.INFO
    }

    @After
    fun tearDown() {
        logger1!!.removeAppender(mockAppender!!)
        logger2!!.removeAppender(mockAppender!!)
    }

    @Test
    fun writeLog_shouldWriteTransactionLogWhenLabelIs_T() {

        val payload = hashMapOf("Label" to "transaction")

        service.writeLogs(payload)

        verify(mockAppender, times(1))!!.append(captorLoggingEvent!!.capture())
        assertEquals(captorLoggingEvent.value.level, Level.INFO)
        assertEquals(captorLoggingEvent.value.loggerName, "logger1")
        assertEquals(captorLoggingEvent.value.message.formattedMessage, "{Label=transaction}")
    }

    @Test
    fun writeLog_shouldWriteBackendLogWhenLabelIs_B() {

        val payload = hashMapOf("Label" to "backend")

        service.writeLogs(payload)

        verify(mockAppender, times(1))!!.append(captorLoggingEvent!!.capture())
        assertEquals(captorLoggingEvent.value.level, Level.INFO)
        assertEquals(captorLoggingEvent.value.loggerName, "logger2")
        assertEquals(captorLoggingEvent.value.message.formattedMessage, "{Label=backend}")
    }
}