package io.pivotal.pal.tracker

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PalTrackerApplication

fun main(args: Array<String>) {
//    SpringApplication.run(PalTrackerApplication::class.java, *args)
    runApplication<PalTrackerApplication>(*args)
}
