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
    }
};

const getters = {
    filters: (state) => state.filters,
    questions: (state) => state.questions,
    categories: (state) => state.categories,
    statistics: (state) => state.statistics,
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
    updateCategory(context, category) {
        context.commit('updateCategory', category);
        context.commit('updatePage', 0);
        context.dispatch('updateQuestionList');
        context.dispatch('updateStatistics', category);
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
        await questionService.upVoteAnswer(answerId);
        context.commit('incrementQuestionAnswerScore', answerId);
        context.commit('setAnswerCanVote', {answerId, canVote: false});
    },
    async downVoteAnswer(context, answerId) {
        await questionService.downVoteAnswer(answerId);
        context.commit('decrementQuestionAnswerScore', answerId);
        context.commit('setAnswerCanVote', {answerId, canVote: false});
    },
    async updateStatistics(context, category) {
        let statistics;
        if (category && category.id) {
            statistics = await questionService.getStatistics(category.id);
        } else {
            statistics = await questionService.getStatistics();
        }
        
        context.commit('updateStatistics', {category, ...statistics});
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
    updateCategory(state, category) {
        state.filters.category = category;
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
    },
    incrementQuestionAnswerScore(state, answerId) {
        const answer = getAnswerById(state, answerId);
        answer.score += 1;
    },
    decrementQuestionAnswerScore(state, answerId) {
        const answer = getAnswerById(state, answerId);
        answer.score -= 1;
    },
    setAnswerCanVote(state, {answerId, value}) {
        const answer = getAnswerById(state, answerId);
        answer.canVote = value;
    },
    updateStatistics(state, statistics) {
        state.statistics = statistics;
    }
};

export default {
    namespaced: true,
    state,
    getters,
    actions,
    mutations
}
