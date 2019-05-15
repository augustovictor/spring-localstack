package com.augustovictor.localstack.filemanager

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectListing
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class FileManagerController(private val amazonS3: AmazonS3) {
    @GetMapping("/list-objects-bucket")
    fun listObjectFromBucket(@RequestParam("bucket-name") bucketName: String): ObjectListing {
        return amazonS3.listObjects(bucketName)
    }

    @PostMapping("/create-bucket")
    fun createBucket(@RequestParam("bucket-name") bucketName: String): String? {
        return try {
            amazonS3.createBucket(bucketName)
            null
        } catch (e: Exception) {
            e.toString()
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

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    fun deleteBucket(@RequestParam("bucket-name") bucketName: String) {
        amazonS3.deleteBucket(bucketName)
    }
}
