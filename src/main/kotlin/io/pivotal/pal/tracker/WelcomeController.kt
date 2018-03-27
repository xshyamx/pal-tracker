package io.pivotal.pal.tracker

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class WelcomeController constructor(@Value("\${WELCOME_MESSAGE:Hello World!}") message: String) {

    val message = message;

    @GetMapping("/")
    fun sayHello():String {
        return message
    }
}


