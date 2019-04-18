package com.augustovictor.localstack

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LocalstackApplication

fun main(args: Array<String>) {
	runApplication<LocalstackApplication>(*args)
}
