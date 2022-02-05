package com.radmir.testClient.testClient

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class TestClientApplication

fun main(args: Array<String>) {
	runApplication<TestClientApplication>(*args)
}
