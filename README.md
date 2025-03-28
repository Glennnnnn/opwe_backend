## OPWE handbook
project deployed on http://13.210.111.21, however, due to EC2 server limitation, not all backend services are deployed now.
Currently available page is the profile page, you can find it at http://13.210.111.21/ProfilePage.
### Basic service logics

All request will come through gateway-service for dispatching and load balance, services are registered to eureka service. Gateway will call auth service through open-feign to do login and verification, then route to the corresponding service or reject the request based on the response of auth-service.
```
opwe-open-service 8081 8082
opwe_eureka_service 8761
opwe_settlement-service 8083 8084
opwe-gateway-service 8085
opwe-auth-service 8086
opwe-management-service 8087
opwe-data-process-service 8088 8089
opwe-elasticsearch-service 8090
opwe-user-service 8091 8092
```

![Login](https://github.com/user-attachments/assets/b83daae3-bcc4-43eb-b2f1-fe66e58e6448)
![Basic request](https://github.com/user-attachments/assets/aa6c3825-d246-4bcc-93fb-68b9eb86a510)

### Project Structure
![opwe](https://github.com/user-attachments/assets/732bd802-62cb-445e-be4d-0b32647cd78e)
