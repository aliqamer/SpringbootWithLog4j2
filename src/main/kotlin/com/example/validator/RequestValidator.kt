package com.example.validator

import org.springframework.stereotype.Service
import org.springframework.validation.Errors
import org.springframework.validation.Validator

@Service
class RequestValidator : Validator {

    override fun validate(target: Any?, errors: Errors?) {

        val payload: HashMap<String, String> = target as HashMap<String, String>

        if(payload.isEmpty()) {
            errors!!.reject("Request", "Request can not be empty!")
        } else {

            var isLabelExist = false

            payload.entries.stream().forEach { entry ->

                if(entry.key.toLowerCase() == "label") {
                    isLabelExist = true
                }
            }

            if(!isLabelExist){
                errors!!.reject("Label", "Request should contain Label param!")
            }
        }
    }

    override fun supports(clazz: Class<*>?): Boolean {
        return HashMap::class.java.equals(clazz)
    }


}