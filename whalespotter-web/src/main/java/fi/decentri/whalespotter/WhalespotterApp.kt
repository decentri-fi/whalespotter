package fi.decentri.whalespotter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class WhalespotterApp

fun main(args: Array<String>) {
    runApplication<WhalespotterApp>(*args)
}