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
