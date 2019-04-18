package com.augustovictor.localstack

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectListing
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.net.URL

@RestController
class FileManagerController(private val amazonS3: AmazonS3) {
    @GetMapping("/list-objects-bucket")
    fun listObjectFromBucket(@RequestParam("bucket-name") bucketName: String): ObjectListing {
        return amazonS3.listObjects(bucketName)
    }

    @PostMapping("/create-bucket")
    fun createBucket(@RequestParam("bucket-name") bucketName: String): String? {
        try {
            amazonS3.createBucket(bucketName)
            return null
        } catch (e: Exception) {
            return e.toString()
        }
    }

    @PostMapping("/upload")
    fun uploadFile(
            @RequestParam("bucket-name") bucketName: String,
            @RequestParam("file") file: MultipartFile
    ) {
        val putObjectRequest = PutObjectRequest(bucketName, file.originalFilename, file.inputStream, null)
        putObjectRequest.cannedAcl = CannedAccessControlList.PublicRead

        amazonS3.putObject(putObjectRequest)
    }

    @GetMapping("/download")
    fun downloadFile(
            @RequestParam("bucket-name") bucketName: String,
            @RequestParam("object-key") objectKey: String
    ): String {
        return amazonS3.getUrl(bucketName, objectKey).toExternalForm()
    }
}
