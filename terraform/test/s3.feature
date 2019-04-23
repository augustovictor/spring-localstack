Feature: S3 buckets should have acl set as private
    In order to improve security

Scenario: S3 acl
    Given I have aws_s3_bucket defined
    When it contains acl
    Then its value must match the "\${var.s3_bucket_acl}" regex

Scenario: S3 should follow naming pattern
    Given I have aws_s3_bucket defined
    When it contains s3_bucket_name
    Then its value must match the "\${var.environment}-.*" regex
