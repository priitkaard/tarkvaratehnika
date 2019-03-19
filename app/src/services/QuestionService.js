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
            return apiService.put(`question/${questionId}/upvote`);
        } else if (direction === 'DOWN') {
            return apiService.put(`question/${questionId}/downvote`);
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