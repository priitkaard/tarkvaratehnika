export default {
    namespaced: true,
    state: {
        isLoggedIn: Boolean(localStorage.getItem('authToken')),
        username: null,
        points: null,
    },
    actions: {
        logIn(context, payload) {
            context.commit('setLoggedIn', true);
            context.commit('setUsername', payload.username);
            context.commit('setPoints', payload.points);
        },
        logOut(context) {
            context.commit('setLoggedIn', false);
            context.commit('setUsername', null);
            context.commit('setPoints', null);
        }
    },
    mutations: {
        setLoggedIn(state, value) {
            state.isLoggedIn = value;
        },
        setUsername(state, username) {
            state.username = username;
        },
        setPoints(state, points){
            state.points = points;
        }
    }
}