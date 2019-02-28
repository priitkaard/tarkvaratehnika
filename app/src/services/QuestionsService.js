import apiService from './ApiService';

export default {
    autoCompleteSuggestions(input) {
        return apiService.get('questions/auto-complete', {
            params: {
                query: input
            }
        });
    },

    postQuestion(data) {
        return apiService.post('questions', {
            title: data.title,
            text: data.description,
            categoryId: data.categoryId
        });
    },

    getQuestions(filters) {
        return apiService.get('questions', {
            params: {
                page: 0,
                limit: 20,
                ...filters
            }
        });
    },

    vote(questionId, direction) {
        // TODO: Instead send request to backend with authenticated user.
        const votedQuestions = JSON.parse(localStorage.getItem('votedQuestions') || '[]');
        if (votedQuestions.includes(questionId)) {
            throw {
                error: 'Shouldn\'t be allowed to vote'
            }
        }
        votedQuestions.push(questionId);
        localStorage.setItem('votedQuestions', JSON.stringify(votedQuestions));

        if (direction === 'UP') {
            return apiService.put(`questions/${questionId}/upvote`);
        } else if (direction === 'DOWN') {
            return apiService.put(`questions/${questionId}/downvote`);
        } else {
            throw {
                error: 'Invalid vote direction!'
            }
        }

    },

    getQuestionCategories() {
        return new Promise(resolve => {
            setTimeout(() => {
                resolve([
                    {id: 1, name: 'Tarkaratehnika'},
                    {id: 2, name: 'Tehisintellekti ja masinõppe alused'},
                    {id: 3, name: 'Tõenäosusteooria ja statistiline matemaatika'},
                    {id: 4, name: 'Kasutajaliidesed'},
                    {id: 5, name: 'Andmbebaasid I'}
                ])
            }, 100);
        })
    }
}