package com.example.controller

import com.example.service.ApiService
import com.example.validator.RequestValidator
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.validation.Errors
import org.springframework.validation.MapBindingResult
import javax.servlet.http.HttpServletResponse

@RunWith(MockitoJUnitRunner::class)
class ApiControllerTest {

    @InjectMocks
    lateinit var apiController: ApiController

    @Mock
    lateinit var service: ApiService

    @Mock
    lateinit var requestValidator: RequestValidator

    @Mock
    lateinit var httpServletResponse: HttpServletResponse

    @Mock
    lateinit var errors: Errors

    lateinit var payload: HashMap<String, String>

    @Before
    fun setup() {
        payload = hashMapOf("timestamp" to "12234355434",
                "Label" to "transaction",
                "error" to "Not Found")

    }

    @Test
    fun writeLogs_ShouldCallServiceWithValidPayload() {

        apiController.writeLogs(payload, errors, httpServletResponse)

        Mockito.verify(service).writeLogs(payload)
        Mockito.verifyZeroInteractions(httpServletResponse)
    }

    @Test
    fun writeLogs_WithEmptyPayload() {

        val emptyPayload = hashMapOf<String, String>()

        var errors: Errors = MapBindingResult(HashMap<Any, Any>(), "")
        errors.reject("Label", "Request can not be empty")

        apiController.writeLogs(emptyPayload, errors, httpServletResponse)

        Mockito.verify(httpServletResponse).sendError(400, "Request can not be empty")
        Mockito.verifyZeroInteractions(service)

    }

}