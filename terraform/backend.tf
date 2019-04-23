provider "aws" {
  region = "us-east-1"

  s3_force_path_style = true

  endpoints {
    s3 = "${var.s3_endpoint}"
  }
}
