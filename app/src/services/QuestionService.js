import apiService from './ApiService';
import _ from 'lodash';
import store from '../store';
import sum from 'lodash/sum';

export default {
    autoCompleteSuggestions(input) {
        return apiService.get('question/auto-complete', {
            params: {
                query: input,
            },
        });
    },

    postQuestion(data) {
        return apiService.post('question', {
            title: data.title,
            text: data.description,
            categoryId: data.categoryId,
        });
    },

    getQuestions(filters) {
        const questionFilters = _.clone(filters, true);

        let categoryId = null;
        if (questionFilters.category) {
            categoryId = questionFilters.category.id;
            delete questionFilters.category;
        }

        return apiService.get('question/list', {
            params: {
                ...questionFilters,
                categoryId,
            },
        });
    },

    getQuestion(id) {
        return apiService.get(`question/${id}`);
    },
    answerQuestion(questionId, text) {
        return apiService.post(`question/${questionId}/answer`, { text });
    },
    async upVote(questionId) {
        return await apiService.put(`/question/${questionId}/upvote`);
    },
    async downVote(questionId) {
        return await apiService.put(`/question/${questionId}/downvote`);
    },
    async upVoteAnswer(answerId) {
        return await apiService.put(`/answer/${answerId}/upvote`);
    },
    async downVoteAnswer(answerId) {
        return await apiService.put(`/answer/${answerId}/downvote`);
    },
    async commentAnswer(answerId, text) {
        return await apiService.post(`/answer/${answerId}/comment`, { text });
    },
    async commentQuestion(questionId, text) {
        return await apiService.post(`/question/${questionId}/comment`, { text });
    },
    async updateQuestion(questionId, data){
        return await apiService.put(`/question/${questionId}`, {
            title: data.title,
            text: data.text,
            categoryId: data.categoryId });
    },
    async updateAnswer(answerId, data){
        return await apiService.put(`/answer/${answerId}`, { text: data.text });
    },
    async getStatistics(category) {
        let url = '/statistics';
        if (category) {
            url = `/statistics?category=${category}`;
        }
        return await apiService.get(url);
    },
    hasVoted(question) {
        return Boolean(
            question &&
            question.votes &&
            question.votes.find(vote => vote.user.username === store.state.auth.username)
        );
    },
    async acceptAnswer(id){
        return await apiService.put(`/answer/${id}/accept`);
    },

    calculateScore(entity) {
        return entity && sum(entity.votes.map(vote => vote.relativeScore)) || 0;
    },
};
