export default {
    namespaced: true,
    state: {
        component: null,
    },
    mutations: {
        updateComponent(state, component) {
            state.component = component;
        },
    },
    actions: {
        createModal(context, component) {
            context.commit('updateComponent', component);
        },
        dismissModal(context) {
            context.commit('updateComponent', null);
        },
    },
};
