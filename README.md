# spring-localstack

Running localstack:
```sh
SERVICES=s3 docker-compose -f docker-compose.localstack.yml up -d
```


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
