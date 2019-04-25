provider "aws" {
  region = "us-east-1"

  s3_force_path_style = true

  endpoints {
    s3 = "http://localhost:4572"
    dynamodb = "http://localhost:4569"
  }
}

resource "aws_s3_bucket" "terraform_state" {
  bucket = "tfstate"

  versioning {
    enabled = true
  }

  lifecycle {
    prevent_destroy = true
  }
}

resource "aws_dynamodb_table" "terraform_state_lock" {
  name = "terraform_state_lock_dev"
  read_capacity = 1
  write_capacity = 1
  hash_key = "LockID"

  "attribute" {
    name = "LockID"
    type = "S"
  }
}
