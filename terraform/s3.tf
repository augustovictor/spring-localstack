module "s3_bucket" {
  source = "../terraform-modules/s3"

  s3_bucket_name = "${var.s3_bucket_name}"
  environment    = "${var.environment}"
}
