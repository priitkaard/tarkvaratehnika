import { LOCAL_STORAGE } from '../../services/AuthService';
import { storageGet } from '../../services/StorageService';

const initalState = {
    isLoggedIn: Boolean(storageGet('authToken')),
    username: storageGet('username'),
};

export default {
    namespaced: true,
    state: initalState,
    actions: {
        logIn(context, payload) {
            context.commit('setLoggedIn', true);
            context.commit('setUsername', payload.username);
        },
        logOut(context) {
            context.commit('setLoggedIn', false);
            context.commit('setUsername', null);
        },
    },
    mutations: {
        setLoggedIn(state, value) {
            state.isLoggedIn = value;
        },
        setUsername(state, username) {
            state.username = username;
        },
    },
};
