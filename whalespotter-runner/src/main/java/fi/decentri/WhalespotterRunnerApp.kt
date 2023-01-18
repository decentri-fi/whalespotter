package fi.decentri

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WhalespotterRunnerApp

fun main(args: Array<String>) {
    runApplication<WhalespotterRunnerApp>(*args)
}