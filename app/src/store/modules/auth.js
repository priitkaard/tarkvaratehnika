export default {
    namespaced: true,
    state: {
        isLoggedIn: !!localStorage.getItem('authToken'),
    },
    actions: {
        logIn(context) {
            context.commit('setLoggedIn', true);
        },
        logOut(context) {
            context.commit('setLoggedIn', false);
        }
    },
    mutations: {
        setLoggedIn(state, value) {
            state.isLoggedIn = value;
        },
    }
}