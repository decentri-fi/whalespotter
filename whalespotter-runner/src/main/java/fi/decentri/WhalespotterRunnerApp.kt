package fi.decentri

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class WhalespotterRunnerApp

fun main(args: Array<String>) {
    runApplication<WhalespotterRunnerApp>(*args)
}