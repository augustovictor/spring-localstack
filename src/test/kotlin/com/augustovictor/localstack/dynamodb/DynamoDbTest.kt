package com.augustovictor.localstack.dynamodb

import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.PutObjectRequest
import org.junit.Rule
import org.junit.Test
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.containers.localstack.LocalStackContainer.Service.S3

class DynamoDbTest {
    @get:Rule
    val localstack = LocalStackContainer().withServices(S3)

    @Test
    fun `should list all tables`() {
        val s3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(localstack.getEndpointConfiguration(S3))
                .withCredentials(localstack.defaultCredentialsProvider)
                .build()

        val bucket = "bucket-name"
        s3.createBucket(bucket)
        s3.putObject(PutObjectRequest(bucket, "object", "My custom data".byteInputStream(), null))
    }
}
