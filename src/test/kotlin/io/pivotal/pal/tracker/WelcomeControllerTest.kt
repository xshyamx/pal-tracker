package io.pivotal.pal.tracker

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class WelcomeControllerTest {

    @Test
    fun sayHello() {
        val controller = WelcomeController("Hello Universe!")
        assertEquals("Hello Universe!", controller.sayHello())
    }
}
