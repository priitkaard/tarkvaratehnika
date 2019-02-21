import apiService from './ApiService';

export default {
    questions: [
        'What is the meaning of life?',
        'How many pets does Jesus have?',
        'How many hours per day I should sleep?',
        'Are cats reborn as angry women?'
    ],

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
    }
}