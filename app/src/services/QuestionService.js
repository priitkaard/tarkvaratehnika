import apiService from './ApiService';

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
        const categoryId = filters.categoryId !== 0 ? filters.categoryId : null;
        return apiService.get('question/list', {
            params: {
                ...filters,
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
}