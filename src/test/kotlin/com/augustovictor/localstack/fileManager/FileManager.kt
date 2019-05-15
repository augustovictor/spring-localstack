package com.augustovictor.localstack.fileManager

import com.augustovictor.localstack.BaseIntegrationTest
import com.augustovictor.localstack.infra.aws.S3Config
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class FileManagerTest : BaseIntegrationTest() {

    @LocalServerPort
    private lateinit var serverPort: Number

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    private lateinit var s3Config: S3Config

    private val bucketName = "my-custom-bucket-api"
    private lateinit var BASE_URL: String

    @Before
    fun setUp() {
        BASE_URL = "http://localhost:$serverPort"
    }

    @Test
    fun `should create bucket with given name`() {
        try {
            val uriCreateBucket = "$BASE_URL/create-bucket?bucket-name=$bucketName"
            testRestTemplate.exchange(uriCreateBucket, HttpMethod.POST, HttpEntity.EMPTY, String::class.java)

            val urilistObjects = "$BASE_URL/list-objects-bucket?bucket-name=$bucketName"
            val response = testRestTemplate.exchange(urilistObjects, HttpMethod.GET, HttpEntity.EMPTY, String::class.java)

            val expectedJson = """{ "bucketName": "$bucketName-WRONG" }"""
            JSONAssert.assertEquals(expectedJson, response.body, JSONCompareMode.LENIENT)
        } finally {
            s3Config.amazonS3().deleteBucket(bucketName)
        }
    }
}
