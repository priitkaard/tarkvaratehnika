import questionService from '../../services/QuestionsService';

const state = {
    filters: {
        query: '',
        sort: 'score',
        direction: 'DESC',
        categoryId: 0
    },
    questions: []
};

const getters = {
    filters: (state) => {
        return state.filters;
    },
    questions: (state) => {
        return state.questions;
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
    async updateQuestionList({ state, commit }) {
        let questions = await questionService.getQuestions(state.filters);

        // TODO: Implement correct solution in backend after authentication is implemented.
        const votedQuestions = JSON.parse(localStorage.getItem('votedQuestions') || '[]');
        questions = questions.map(question => {
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
        state.questions = state.questions.map(question => {
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
