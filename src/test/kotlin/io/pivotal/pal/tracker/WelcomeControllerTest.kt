package io.pivotal.pal.tracker

import org.junit.Assert.assertEquals
import org.junit.Test

class WelcomeControllerTest {

    @Test
    fun sayHello() {
        val controller = WelcomeController("Hello Universe!")
        assertEquals("Hello Universe!", controller.sayHello())
    }
}
