package com.augustovictor.localstack

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3Client
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

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
}
