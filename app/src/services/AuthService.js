import axios from 'axios';

export default {
    async tryLogin(username, password) {
        const response = await axios.post(`http://localhost:8080/login`, { username, password });
        console.log(response.headers);

        const token = response.headers['Authorization'];
        localStorage.setItem('authToken', token);

        return token;
    }
}