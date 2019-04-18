package com.augustovictor.localstack

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.Exception

@Configuration
class S3Config(
        @Value("\${aws.s3.uri}") private var s3Uri: String
) {
    @Bean
    fun amazonS3(): AmazonS3 {
        val s3ClientBuilder = AmazonS3Client.builder()
        s3ClientBuilder.withPathStyleAccessEnabled(true)
        val endpointConfiguration = AwsClientBuilder.EndpointConfiguration(s3Uri, "us-east-1")
        if (s3Uri != "") s3ClientBuilder.withEndpointConfiguration(endpointConfiguration)
        return s3ClientBuilder.build()
    }

    @Bean
    fun applicationRunnerS3(amazonS3: AmazonS3): Boolean {
        try {
            amazonS3.createBucket("my-custom-bucket")
        } catch(e: Exception) {
            println(e)
        }

        println("Buckets:")
        amazonS3.listBuckets().forEach(::println)

//        val bucket = "my-custom-bucket"
//        amazonS3.createBucket(bucket)
//
//        amazonS3.putObject(PutObjectRequest(bucket, "object", "My custom data".byteInputStream(), null))

        return true
    }
}
