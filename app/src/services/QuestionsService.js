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
            text: data.description
        });
    },

    getQuestions(filters) {
        return apiService.get('questions', {
            ...filters
        });
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