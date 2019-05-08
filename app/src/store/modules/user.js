const initialState = {
    points: null,
};

export default {
    namespaced: true,
    state: initialState,
    mutations: {
        setPoints(state, points) {
            state.points = points;
        },
    },
};
