Feature: S3 buckets should have acl set as private
    In order to improve security

Scenario: S3 acl
    Given I have aws_s3_bucket defined
    Then it must contain acl
