terraform {
  required_version = ">= 0.11.9"
  backend "s3" {}
}

provider "aws" {
  region = "us-east-1"

  s3_force_path_style = true

  endpoints {
    s3 = "${var.environment == "development" ? var.s3_endpoint : ""}"
    dynamodb = "${var.environment == "development" ? var.dynamodb_endpoint : ""}"
  }
}
