# Deployment
S3 Bucket endpoint: http://tutkit.s3-website.eu-north-1.amazonaws.com/
API (EC2 instance): 3.82.117.200:8080
PostgreSQL (RDS instance): tutkit-instance.c4g3ku9rpltc.us-east-1.rds.amazonaws.com

# Authentication
Most endpoints require authentication.
Register at:
POST /users/sign-up
Header: Content-type application/json
{
	"username": "asd",
	"password": "asd"
}


Login at:
POST /login
Header: Content-type application/json
{
	"username": "asd",
	"password": "asd"
}

You will get back:
Bearer <jwt_here>

With every request, you need to include the header
Authorization: Bearer <jwt_here>

Read the automatically generated API documentaton at:
http://localhost:8080/swagger-ui.html
