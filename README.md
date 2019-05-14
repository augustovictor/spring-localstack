# spring-localstack

## About
This project is a working example built with `kotlin` + `spring framework` on how to use `localstack` for testing aws cloud environment.

Also it makes use of `terraform` to automate the creation of the cloud infrastructure, and `terraform-compliance` to ensure terraform is tested following a BDD strategy.

## Stack used

<img src="./img/kotlin-logo.png" alt="kotlin" width="200"/>

<img src="./img/spring-logo.png" alt="spring" width="200"/>

<img src="./img/localstack.png" alt="localstack" width="200"/> [LocalStack](https://github.com/localstack/localstack)

<img src="./img/terraform.png" alt="terraform" width="200"/>

<img src="./img/terraform-compliance.png" alt="terraform" width="300"/>


## Localstack

Running localstack:
```sh
PORT_WEB_UI=9000 SERVICES=s3,dynamodb docker-compose -f docker-compose.localstack.yml up -d
```
On mac:
```sh
TMPDIR=/private$TMPDIR PORT_WEB_UI=9000 SERVICES=s3,dynamodb docker-compose -f docker-compose.localstack.yml up -d
```


## Terraform:

#### Create initial structure (s3 Bucket for remote state + dynamodb Table for state locking)
Init:
```bash
cd dev/remote-state
aws-vault --debug exec scd-stg -- terraform init
```

Plan:
```bash
aws-vault --debug exec scd-stg -- terraform plan -out execution-plan.tfplan
```

Apply:
```bash
aws-vault --debug exec scd-stg -- terraform apply execution-plan.tfplan
```

#### Create initial infra (s3 Bucket for application use)
Init:
```bash
# spring-localstack/terraform
aws-vault --debug exec scd-stg -- terraform init -backend-config=dev/backend.conf
```

Plan:
```bash
aws-vault --debug exec scd-stg -- terraform plan -out execution-plan.tfplan -var-file=dev/terraform.tfvars
aws-vault --debug exec scd-stg -- terraform plan -var-file=dev/terraform.tfvars
```

## Terraform compliance
Running tests:
```sh
docker run --rm -v $PWD:/target -i -t eerkunt/terraform-compliance -f terraform-modules/test -t terraform-modules/s3
```

## API calls

Create bucket:
```sh
POST / HTTP/1.1
Host: localhost:9090/create-bucket?bucket-name=created-bucket-api
```

List objects from bucket:
```sh
GET / HTTP/1.1
Host: localhost:9090/list-objects-bucket?bucket-name=created-bucket-api
```

Upload object to bucket:
```sh
GET / HTTP/1.1
Host: localhost:9090/upload?bucket-name=created-bucket-api

Content-type: multipart/form-data
```

Download object from bucket:
```sh
GET / HTTP/1.1
Host: localhost:9090/download?bucket-name=created-bucket-api&object-key=application.properties
```

## Steps

## Known Issues
- Not possible to use 1 `terraform.tfstate` for multiple environments. The `terraform.tfstate` file will be overwritten;
- When using `remote state` you should first create the `bucket` and `dynamodb` table for locking;
- Names different from [aws services](https://docs.aws.amazon.com/cli/latest/reference/#available-services);
- Different credentials used when `terraform init` for the `remote-state` and `dev/staging` env;
