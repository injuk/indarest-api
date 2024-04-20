package rest.mjis.indarest

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class IndarestApplication

fun main(args: Array<String>) {
	runApplication<IndarestApplication>(*args)
}
