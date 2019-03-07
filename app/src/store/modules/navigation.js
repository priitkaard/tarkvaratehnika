export default {
    namespaced: true,
    state: {
        drawer: false,
        menuItems: [
            {
                id: 1,
                name: 'Logi sisse',
            },
        ],
    },
    actions: {
        openDrawer(context) {
            context.commit('setDrawer', true);
        },
        closeDrawer(context) {
            context.commit('setDrawer', false);
        },
    },
    mutations: {
        setDrawer(state, value) {
            state.drawer = value;
        },
    },
};