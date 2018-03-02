package com.example.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ApiService {

    var logger1: Logger = LoggerFactory.getLogger("logger1")
    var logger2: Logger = LoggerFactory.getLogger("logger2")

    fun writeLogs(payload: Map<String, String>){

        if(payload["Label"] == "transaction"){
            logger1.info("{}",payload)
        } else {
            logger2.info("{}",payload)
        }
    }
}