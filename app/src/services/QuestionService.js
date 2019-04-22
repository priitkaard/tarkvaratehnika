import apiService from './ApiService';
import _ from 'lodash';

export default {
    autoCompleteSuggestions(input) {
        return apiService.get('question/auto-complete', {
            params: {
                query: input
            }
        });
    },

    postQuestion(data) {
        return apiService.post('question', {
            title: data.title,
            text: data.description,
            categoryId: data.categoryId
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
                categoryId
            }
        });
    },

    getQuestion(id) {
        return apiService.get(`question/${id}`);
    },
    answerQuestion(questionId, text) {
        return apiService.post(`question/${questionId}/answer`, { text });
    },
    async upVote(questionId) {
        // TODO: Instead send request to backend with authenticated user.
        const votedQuestions = JSON.parse(localStorage.getItem('votedQuestions') || '[]');
        if (votedQuestions.includes(questionId)) {
            throw {
                error: 'Shouldn\'t be allowed to vote'
            }
        }
        votedQuestions.push(questionId);
        localStorage.setItem('votedQuestions', JSON.stringify(votedQuestions));

        return await apiService.put(`/question/${questionId}/upvote`);
    },
    async downVote(questionId) {
        // TODO: Instead send request to backend with authenticated user.
        const votedQuestions = JSON.parse(localStorage.getItem('votedQuestions') || '[]');
        if (votedQuestions.includes(questionId)) {
            throw {
                error: 'Shouldn\'t be allowed to vote'
            }
        }
        votedQuestions.push(questionId);
        localStorage.setItem('votedQuestions', JSON.stringify(votedQuestions));

        return await apiService.put(`/question/${questionId}/downvote`);
    },
    async upVoteAnswer(answerId) {
        return await apiService.put(`/answer/${answerId}/upvote`);
    },
    async downVoteAnswer(answerId) {
        return await apiService.put(`/answer/${answerId}/downvote`);
    },
    async commentAnswer(answerId, text) {
        return await apiService.post(`/answer/${answerId}/comment`, {text});
    },
    async commentQuestion(questionId, text) {
        return await apiService.post(`/question/${questionId}/comment`, {text});
    },
    async updateQuestion(questionId, data){
        return await apiService.put(`/question/${questionId}`, {
        title: data.title,
        text: data.text,
        categoryId: data.categoryId})
    },

    getStatistics(category) {
        if (category) {
            return apiService.get(`/statistics?category=${category}`);
        }
        return apiService.get('/statistics');
    }
}
