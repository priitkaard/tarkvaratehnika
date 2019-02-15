const questions = [
    'What is the meaning of life?',
    'How many pets does Jesus have?',
    'How many hours per day I should sleep?',
    'Are cats reborn as angry women?'
];

export default {
    autoCompleteSuggestions(input) {
        return new Promise((resolve) => {
            setTimeout(() => {
                resolve(questions.filter(q => q.toLowerCase().includes(input.toLowerCase())));
            }, 100);
        });
    }
}