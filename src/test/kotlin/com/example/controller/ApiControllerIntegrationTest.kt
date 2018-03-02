package com.example.controller

import com.example.service.ApiService
import com.example.validator.RequestValidator
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@WebMvcTest
internal class ApiControllerIntegrationTest {

    @MockBean
    lateinit var service: ApiService

    @MockBean
    lateinit var requestValidator: RequestValidator

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    val payload = hashMapOf("timestamp" to "2342323244",
            "Label" to "transaction",
            "error" to "No error")
    
    @Test
    fun writeLogs_ShouldPass() {

        this.mockMvc.perform(MockMvcRequestBuilders.post("/example/write")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
                .andExpect(MockMvcResultMatchers.status().`is`(200))
    }

}