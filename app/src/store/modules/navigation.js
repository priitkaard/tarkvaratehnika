import authService from '../../services/AuthService';

export default {
    namespaced: true,
    state: {
        drawer: false,
        menuItems: [
            {
                id: 1,
                name: 'Log in',
                action: context => context.dispatch('modal/createModal', 'Login', { root: true }),
                loggedOut: true,
            },
            {
                id: 2,
                name: 'Log out',
                action: () => {
                    authService.logOut();
                },
                loggedIn: true,
            }
        ],
    },
    getters: {
        getMenuItems(state, getters, rootState) {
            if (rootState.auth.isLoggedIn) {
                return state.menuItems.filter(item => item.loggedOut !== true);
            }
            return state.menuItems.filter(item => item.loggedIn !== true);
        }
    },
    actions: {
        openDrawer(context) {
            context.commit('setDrawer', true);
        },
        closeDrawer(context) {
            context.commit('setDrawer', false);
        },
        chooseItem(context, item) {
            item.action(context);
        }
    },
    mutations: {
        setDrawer(state, value) {
            state.drawer = value;
        },
    },
};