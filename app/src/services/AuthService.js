import axios from 'axios';
import store from '../store';
import apiService, { handleErrors } from './ApiService';
import { LOCAL_STORAGE, storageRemove, storageSet } from './StorageService';
import { initializeUser } from './UserService';


export async function doLogin(token, username) {
    storageSet(LOCAL_STORAGE.AUTH_TOKEN, token);
    storageSet(LOCAL_STORAGE.USERNAME, username);

    store.dispatch('auth/logIn', { username });

    await initializeUser();
}

export function logOut() {
    storageRemove(LOCAL_STORAGE.AUTH_TOKEN);
    storageRemove(LOCAL_STORAGE.USERNAME);

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
            password,
        });
    } catch (err) {
        handleErrors(err);
    }
}

export function isCurrentUser(user) {
    return user && user.username === store.state.auth.username;
}
