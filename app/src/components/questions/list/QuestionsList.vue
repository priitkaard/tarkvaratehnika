<template>
    <div class="QuestionsList">
        <QuestionsListElement
                v-for="question in questions"
                v-bind:key="question.id"
                v-bind:question="question"
                v-on:vote="voteQuestion" />
    </div>
</template>

<script>
    import QuestionsListElement from './QuestionsListElement';
    import questionService from '../../../services/QuestionsService';

    export default {
        name: "QuestionsList",
        props: ['filters'],
        components: {
            QuestionsListElement
        },
        data() {
            return {
                questions: []
            }
        },
        methods: {
            async updateQuestions() {
                const votedQuestions = JSON.parse(localStorage.getItem('votedQuestions') || '[]');

                this.questions = await questionService.getQuestions(this.filters);
                this.questions = this.questions.map(question => {
                    question.canVote = !votedQuestions.includes(question.id);
                    return question;
                })
            },
            voteQuestion({id, direction}) {
                this.questions.map(question => {

                    if (question.id !== id) {
                        return question;
                    }

                    if (direction === 'UP') {
                        question.score += 1;
                    } else if (direction === 'DOWN') {
                        question.score -= 1;
                    } else {
                        return question;
                    }

                    // TODO: Instead use questionService to vote for question and send request to backend.
                    const votedQuestions = JSON.parse(localStorage.getItem('votedQuestions') || '[]');
                    votedQuestions.push(id);
                    localStorage.setItem('votedQuestions', JSON.stringify(votedQuestions));

                    question.canVote = false;
                    return question;
                })
            }
        },
        created() {
            this.updateQuestions();
        },
        watch: {
            async filters() {
                this.updateQuestions();
            }
        },
    }
</script>

<style scoped>
    .QuestionsList {
        margin-top: -10px;
    }
</style>