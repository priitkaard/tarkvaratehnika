const state = {
    filters: {
        query: 'query1',
        sort: 'score',
        direction: 'DESC',
        categoryId: 5
    }
};

const getters = {
    filters: (state) => {
        return state.filters;
    }
};

const actions = {
    updateSort(context, sort) {
        context.commit('updateSort', sort);
    },
    updateQuery(context, query) {
        context.commit('updateQuery', query);
    },
    updateCategoryId(context, categoryId) {
        context.commit('updateCategoryId', categoryId);
    },
    updateQuestionList(context) {

    }
};
const mutations = {
    updateSort(state, sort) {
        state.filters.sort = sort;
    },
    updateQuery(state, query) {
        state.filters.query = query;
    },
    updateCategoryId(state, categoryId) {
        state.filters.categoryId = categoryId;
    }
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
