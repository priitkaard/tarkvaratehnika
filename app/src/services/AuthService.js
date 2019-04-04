import axios from 'axios';
import store from '../store';
import apiService from './ApiService';

export default {
    doLogin(token, username) {
        localStorage.setItem('authToken', token);
        localStorage.setItem('username', username);
        store.dispatch('auth/logIn', { username });
    },

    logOut() {
        localStorage.removeItem('authToken');
        localStorage.removeItem('username');
        store.dispatch('auth/logOut');
    },

    async tryLogin(username, password) {
        const response = await axios.post(`${apiService.API_URL}login`, { username, password });

        const token = response.headers.authorization;
        this.doLogin(token, username);

        return token;
    },

    async register(username, password) {
        await axios.post(`${apiService.API_URL}user/sign-up`, {
            username,
            password
        });
    }
}