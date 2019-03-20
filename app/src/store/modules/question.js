import questionService from '../../services/QuestionService';

function getQuestionById(state, questionId) {
    return state.questions.questions.filter(q => q.id === questionId)[0];
}

const state = {
    filters: {
        page: 0,
        limit: 10,
        query: '',
        sort: 'score',
        direction: 'DESC',
        categoryId: 0
    },
    questions: {
        questions: [],
        totalPages: 0
    },
    categories: null
};

const getters = {
    filters: (state) => state.filters,
    questions: (state) => state.questions,
    categories: (state) => state.categories
};

const actions = {
    updateQuery({ commit }, query) { commit('updateQuery', query) },
    updatePage(context, page) {
        context.commit('updatePage', page);
        context.dispatch('updateQuestionList');
    },
    updateSort(context, sort) {
        context.commit('updateSort', sort);
        context.commit('updatePage', 0);
        context.dispatch('updateQuestionList');
    },
    updateCategoryId(context, categoryId) {
        context.commit('updateCategoryId', categoryId);
        context.commit('updatePage', 0);
        context.dispatch('updateQuestionList');
    },
    async updateQuestionList({ state, commit }) {
        let questions = await questionService.getQuestions(state.filters);

        // TODO: Implement correct solution in backend after authentication is implemented.
        const votedQuestions = JSON.parse(localStorage.getItem('votedQuestions') || '[]');
        questions.questions = questions.questions.map(question => {
            question.canVote = !votedQuestions.includes(question.id);
            return question;
        });

        commit('updateQuestionList', questions);
    },
    async upVote(context, questionId) {
        await questionService.upVote(questionId);
        context.commit('incrementQuestionScore', questionId);
        context.commit('setCanVote', {questionId, canVote: false});
    },
    async downVote(context, questionId) {
        await questionService.downVote(questionId);
        context.commit('decrementQuestionScore', questionId);
        context.commit('setCanVote', {questionId, canVote: false});
    },
    async upVoteAnswer(context, answerId) {

    },
    async downVoteAnswer(context, answerId) {

    }
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
    updateCategoryId(state, categoryId) {
        state.filters.categoryId = categoryId;
    },
    updateQuestionList(state, questions) {
        state.questions = questions
    },
    incrementQuestionScore(state, questionId) {
        const question = getQuestionById(state, questionId);
        question.score += 1;
    },
    decrementQuestionScore(state, questionId) {
        const question = getQuestionById(state, questionId);
        question.score -= 1;
    },
    setCanVote(state, {questionId, value}) {
        const question = getQuestionById(state, questionId);
        question.canVote = value;
    }
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
