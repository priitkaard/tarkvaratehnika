export default {
    namespaced: true,
    state: {
        isLoggedIn: false,
        user: null
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
        setUser(state, user) {
            state.user = user;
        }
    }
}