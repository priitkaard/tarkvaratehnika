import axios from 'axios';
import store from '../store';
import apiService, { handleErrors } from './ApiService';

export default {
    doLogin(token, username, points) {
        localStorage.setItem('authToken', token);
        store.dispatch('auth/logIn', { username, points });
    },

    logOut() {
        localStorage.removeItem('authToken');
        store.dispatch('auth/logOut');
    },

    async tryLogin(username, password) {
        let response;
        try {
            response = await axios.post(`${apiService.API_URL}login`, { username, password });
        } catch (err) {
            handleErrors(err);
        }

        let points = await apiService.get(`/user/points/${username}`);

        const token = response.headers.authorization;
        this.doLogin(token, username, points);

        return token;
    },

    async register(username, password) {
        try {
            return await axios.post(`${apiService.API_URL}user/sign-up`, {
                username,
                password
            });
        } catch (err) {
            handleErrors(err);
        }
    },

}