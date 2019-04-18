package com.augustovictor.localstack

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.services.dynamodb.DynamoDBClient
import software.amazon.awssdk.services.dynamodb.model.*
import java.net.URI

@Configuration
class DynamoConfig(
        @Value("\${aws.dynamodb.uri}") private val dynamoUri: URI?
) {

    @Bean
    fun dynamoDbClient(): DynamoDBClient {
        val builder = DynamoDBClient.builder()

        if (dynamoUri != null) builder.endpointOverride(dynamoUri)

        return builder.build()
    }

//    @Bean
//    fun applicationRunner(dynamoDBClient: DynamoDBClient): List<String> {
//        val createTableRequest = CreateTableRequest
//                .builder()
//                .tableName("all-books")
//                .keySchema(KeySchemaElement.builder().keyType(KeyType.HASH).attributeName("id").build())
//                .attributeDefinitions(AttributeDefinition.builder().attributeName("id").attributeType(ScalarAttributeType.S).build())
//                .provisionedThroughput(ProvisionedThroughput.builder().writeCapacityUnits(5L).readCapacityUnits(5L).build())
//                .build()
//
//        dynamoDBClient.createTable(createTableRequest)
//
//        val tableNames = dynamoDBClient.listTables(ListTablesRequest.builder().build()).tableNames()
//
//        tableNames.forEach(::println)
//
//        return tableNames
//    }
}
