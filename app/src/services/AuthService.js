import axios from 'axios';
import store from '../store';
import apiService, { handleErrors } from './ApiService';

const LOCAL_STORAGE = {
    AUTH_TOKEN: 'authToken',
};

export async function doLogin(token, username) {
    localStorage.setItem(LOCAL_STORAGE.AUTH_TOKEN, token);
    store.dispatch('auth/logIn', { username });

    const points = await apiService.get(`/user/points/${username}`);
    store.commit('user/setPoints', points);
}

export function logOut() {
    localStorage.removeItem(LOCAL_STORAGE.AUTH_TOKEN);
    store.dispatch('auth/logOut');
}

export async function tryLogin(username, password) {
    let response;
    try {
        response = await axios.post(`${apiService.API_URL}login`, { username, password });
    } catch (err) {
        handleErrors(err);
    }

    const token = response.headers.authorization;
    await doLogin(token, username);

    return token;
}

export async function register(username, password) {
    try {
        return await axios.post(`${apiService.API_URL}user/sign-up`, {
            username,
            password
        });
    } catch (err) {
        handleErrors(err);
    }
}
