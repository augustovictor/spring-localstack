resource "aws_s3_bucket" "s3_bucket" {
  bucket = "${var.s3_bucket_name}"
  acl    = "${var.s3_bucket_acl}"

  tags {
    Name        = "${var.s3_bucket_name}"
    Environment = "${var.environment}"
  }
}
