Feature: S3 buckets should be properly formatted
    In order to keep conceptual integrity
    As engineers
    We'll enforce proper naming strategies

Scenario Outline: S3 acl
    Given I have aws_s3_bucket defined
    When it contains <key>
    Then its value must match the "<value>" regex

    Examples:
    | key | value |
    | acl | \${var.s3_bucket_acl} |

Scenario: S3 should follow naming pattern
    Given I have aws_s3_bucket defined
    When it contains bucket
    Then its value must match the "\${var.environment}-.*" regex
