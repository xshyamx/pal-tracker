package io.pivotal.pal.tracker

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EnvController constructor(
        @Value("\${PORT:NOT SET}") port:String,
        @Value("\${MEMORY_LIMIT:NOT SET}") memoryLimit:String,
        @Value("\${CF_INSTANCE_INDEX:NOT SET}") cfInstanceId:String,
        @Value("\${CF_INSTANCE_ADDR:NOT SET}") cfInstanceAddr:String
) {

    val port = port
    val memoryLimit = memoryLimit
    val cfInstanceId = cfInstanceId
    val cfInstanceAddr = cfInstanceAddr

    @GetMapping("/env")
    fun getEnv():Map<String, String> {
        val env:MutableMap<String, String> = HashMap()
        env.put("PORT", port)
        env.put("MEMORY_LIMIT", memoryLimit)
        env.put("CF_INSTANCE_INDEX", cfInstanceId)
        env.put("CF_INSTANCE_ADDR", cfInstanceAddr)
        return env
    }
}