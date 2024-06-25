## use this curl to register

```
`curl --location 'http://localhost:8080/api/v1/auth/register' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "maneesha",
    "lastName": "gunawardhana",
    "email": "maneeha@gmail.com",
    "password": "12345"
}'
```

## use this curl to authenticate

```
curl --location 'http://localhost:8080/api/v1/auth/authenticate' \
--header 'Content-Type: application/json' \
--data-raw '{
    "email": "maneeha@gmail.com",
    "password": "12345"
}'
```

## auth curl please genrate before token to get token

```
curl --location 'http://localhost:8080/api/v1/authorized' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYW5lZWhhQGdtYWlsLmNvbSIsImlhdCI6MTcxOTMxMzUxNSwiZXhwIjoxNzE5MzE0OTU1fQ.GDrDsbbpwq2Q29nWFFsrWdTP8j7xqmIzsm1a-0HN0M0'
```
