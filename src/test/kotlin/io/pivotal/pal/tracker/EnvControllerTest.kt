package io.pivotal.pal.tracker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class EnvControllerTest {
    @Test
    fun getEnv() {
        val ctrl = EnvController("8080", "1G", "0", "cfapps.io")
        val env = ctrl.getEnv()
        assertThat(env.get("PORT")).isEqualTo("8080")
        assertThat(env.get("MEMORY_LIMIT")).isEqualTo("1G")
        assertThat(env.get("CF_INSTANCE_INDEX")).isEqualTo("0")
        assertThat(env.get("CF_INSTANCE_ADDR")).isEqualTo("cfapps.io")
    }
}