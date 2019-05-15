package com.augustovictor.localstack

import org.junit.Rule
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.containers.localstack.LocalStackContainer.Service.S3

@ContextConfiguration(initializers = [BaseIntegrationTest.Initializer::class])
abstract class BaseIntegrationTest {

    companion object {
        @get:Rule
        val localStackContainer = LocalStackContainer().withServices(S3).apply { start() }
    }

    object Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            val values = TestPropertyValues.of("aws.s3.uri=${localStackContainer.getEndpointConfiguration(S3).serviceEndpoint}")

            values.applyTo(applicationContext)
        }
    }
}
