import axios from 'axios';
import store from '../store';
import apiService from './ApiService';

export default {
    doLogin(token) {
        localStorage.setItem('authToken', token);
        store.dispatch('auth/logIn');
    },

    logOut() {
        localStorage.removeItem('authToken');
        store.dispatch('auth/logOut');
    },

    async tryLogin(username, password) {
        const response = await axios.post(`${apiService.API_URL}login`, { username, password });

        const token = response.headers.authorization;
        this.doLogin(token);

        return token;
    },

    async register(username, password) {
        await axios.post(`${apiService.API_URL}users/sign-up`, {
            username,
            password
        });
    }
}