package com.example.validator

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.runners.MockitoJUnitRunner
import org.springframework.validation.Errors
import org.springframework.validation.MapBindingResult
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@RunWith(MockitoJUnitRunner::class)
class RequestValidatorTest {

    @InjectMocks
    lateinit var requestValidator: RequestValidator

    @Test
    fun validate_ShouldPassWithValidPayload() {

        var payload = hashMapOf("timestamp" to "2124243232",
                "Label" to "transaction",
                "message" to "No messasge available")

        var errors: Errors = MapBindingResult(HashMap<String, String>(), "")

        requestValidator.validate(payload, errors)

        assertFalse(errors.hasErrors())
    }

    @Test
    fun validate_ShouldReturnErrorWithEmptyPayload() {

        var errors: Errors = MapBindingResult(HashMap<String, String>(), "")

        requestValidator.validate(HashMap<String, String>(),errors)

        assertTrue(errors.hasErrors())
        assertEquals(errors.allErrors[0].defaultMessage, "Request can not be empty!")


    }

    @Test
    fun supports() {
        Assert.assertTrue(requestValidator.supports(HashMap::class.java))
        assertFalse(requestValidator.supports(JvmType.Object::class.java))
    }

}