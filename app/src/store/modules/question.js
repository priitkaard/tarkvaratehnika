import questionService from '../../services/QuestionsService';

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
        context.dispatch('updateQuestionList');
    },
    updateCategoryId(context, categoryId) {
        context.commit('updateCategoryId', categoryId);
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
    async voteQuestion(context, { id, direction }) {

        if (!['UP', 'DOWN'].includes(direction)) {
            throw {
                error: 'Programming error',
                message: 'Invalid question vote direction!'
            };
        }

        await questionService.vote(id, direction);
        const relativeScore = direction === 'UP' ? 1 : -1;
        context.commit('updateQuestionScore', {id, relativeScore});
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
    updateQuestionScore(state, {id, relativeScore}) {
        state.questions.questions = state.questions.questions.map(question => {
            if (question.id !== id) {
                return question;
            }
            question.score += relativeScore;
            question.canVote = false;
            return question;
        })

    }
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
