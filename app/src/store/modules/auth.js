export default {
    namespaced: true,
    state: {
        isLoggedIn: Boolean(localStorage.getItem('authToken')),
        username: localStorage.getItem('username'),
    },
    actions: {
        logIn(context, payload) {
            context.commit('setLoggedIn', true);
            context.commit('setUsername', payload.username);
        },
        logOut(context) {
            context.commit('setLoggedIn', false);
            context.commit('setUsername', null);
        }
    },
    mutations: {
        setLoggedIn(state, value) {
            state.isLoggedIn = value;
        },
        setUsername(state, username) {
            state.username = username;
        },
    }
}