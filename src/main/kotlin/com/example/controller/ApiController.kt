package com.example.controller

import com.example.service.ApiService
import com.example.validator.RequestValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
class ApiController {

    @Autowired
    lateinit var apiService: ApiService

    @Autowired
    lateinit var requestValidator: RequestValidator

    @RequestMapping("/example/write", method = arrayOf(RequestMethod.POST))
    fun writeLogs(@RequestBody payload: Map<String, String>, errors: Errors, response: HttpServletResponse ){

        requestValidator.validate(payload, errors)

        if(errors.hasErrors()){
            return response.sendError(HttpStatus.BAD_REQUEST.value(), errors.allErrors[0].defaultMessage)
        }
        apiService.writeLogs(payload)
    }
}