variable "s3_bucket_name" {
  description = "Bucket name"
}

variable "s3_endpoint" {
  description = "S3 Endpoint"
  default = ""
}

variable "dynamodb_endpoint" {
  description = "DynamoDb Endpoint"
  default = ""
}

variable "environment" {
  description = "Environment"
}
