import questionService from '../../services/QuestionService';

function getQuestionById(state, questionId) {
    return state.questions.questions.filter(q => q.id === questionId)[0];
}
function getAnswerById(state, answerId) {
    state.questions.questions.forEach(question => {
        question.answers.forEach(answer => {
            if (answer.id === answerId) {
                return answer;
            }
        });
    });
    return null;
}

const state = {
    filters: {
        page: 0,
        limit: 10,
        query: '',
        sort: 'score',
        direction: 'DESC',
        category: null,
    },
    questions: {
        questions: [],
        totalPages: 0,
    },
    categories: null,
    statistics: {
        category: null,
        questions: 0,
        answers: 0,
        comments: 0,
        users: 0,
    },
};

const getters = {
    filters: (state) => state.filters,
    questions: (state) => state.questions,
    categories: (state) => state.categories,
    statistics: (state) => state.statistics,
};

const actions = {
    updateQuery({ commit }, query) { commit('updateQuery', query); },
    updatePage(context, page) {
        context.commit('updatePage', page);
        context.dispatch('updateQuestionList');
    },
    updateSort(context, sort) {
        context.commit('updateSort', sort);
        context.commit('updatePage', 0);
        context.dispatch('updateQuestionList');
    },
    updateCategory(context, category) {
        context.commit('updateCategory', category);
        context.commit('updatePage', 0);
        context.dispatch('updateQuestionList');
        context.dispatch('updateStatistics', category);
    },
    async updateQuestionList({ state, commit }) {
        let questions = await questionService.getQuestions(state.filters);
        commit('updateQuestionList', questions);
    },
    async updateStatistics(context, category) {
        let statistics;
        if (category && category.id) {
            statistics = await questionService.getStatistics(category.id);
        } else {
            statistics = await questionService.getStatistics();
        }
        
        context.commit('updateStatistics', {category, ...statistics});
    }, 
};
const mutations = {
    updatePage(state, page) {
        state.filters.page = page;
    },
    updateSort(state, sort) {
        state.filters.sort = sort;
    },
    updateQuery(state, query) {
        state.filters.query = query;
    },
    updateCategory(state, category) {
        state.filters.category = category;
    },
    updateQuestionList(state, questions) {
        state.questions = questions;
    },
    incrementQuestionScore(state, questionId) {
        const question = getQuestionById(state, questionId);
        question.score += 1;
    },
    decrementQuestionScore(state, questionId) {
        const question = getQuestionById(state, questionId);
        question.score -= 1;
    },
    incrementQuestionAnswerScore(state, answerId) {
        const answer = getAnswerById(state, answerId);
        answer.score += 1;
    },
    decrementQuestionAnswerScore(state, answerId) {
        const answer = getAnswerById(state, answerId);
        answer.score -= 1;
    },
    updateStatistics(state, statistics) {
        state.statistics = statistics;
    },
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations,
};
