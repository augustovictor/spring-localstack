variable "s3_bucket_name" {
  description = "Bucket name"
}

variable "s3_bucket_acl" {
  default = "private"
}

variable "environment" {
  description = "Environment"
}
