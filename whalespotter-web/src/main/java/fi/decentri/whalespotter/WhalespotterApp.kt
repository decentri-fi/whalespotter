package fi.decentri.whalespotter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WhalespotterApp {

}

fun main(args: Array<String>) {
    runApplication<WhalespotterApp>(*args)
}