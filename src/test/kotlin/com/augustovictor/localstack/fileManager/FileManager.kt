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

    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    private lateinit var s3Config: S3Config

    @LocalServerPort
    private lateinit var serverPort: Number

    private val bucketName = "my-custom-bucket-api"

    @Before
    fun setUp() {
        s3Config.amazonS3().createBucket(bucketName)
    }

    @After
    fun tearDown() {
        s3Config.amazonS3().deleteBucket(bucketName)
    }

    @Test
    fun `should list objects from bucket`() {
        val uri = "http://localhost:$serverPort/list-objects-bucket?bucket-name=$bucketName"
        val response = testRestTemplate.exchange(uri, HttpMethod.GET, HttpEntity.EMPTY, String::class.java)

        val expectedJson = """{ "bucketName": "$bucketName" }"""

        JSONAssert.assertEquals(expectedJson, response.body, JSONCompareMode.LENIENT)
    }
}
