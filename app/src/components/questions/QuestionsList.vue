<template>
    <div class="QuestionsList">
        <QuestionsListElement v-for="question in questions" v-bind:key="question.id" v-bind:question="question"/>
    </div>
</template>

<script>
    import QuestionsListElement from './QuestionsListElement';

    export default {
        name: "QuestionsList",
        components: {
            QuestionsListElement
        },
        data() {
            return {
                questions: [
                    {
                        id: 1,
                        title: 'What\'s the meaning of life?',
                        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
                        score: 32,
                        category: {
                            id: 1,
                            name: 'Various'
                        },
                        created: '2018-12-31T12:00:00+00:00',
                        views: 1315,
                        answers: 2,
                        canVote: true
                    },
                    {
                        id: 2,
                        title: 'What\'s the meaning of life?',
                        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
                        score: 32,
                        category: {
                            id: 1,
                            name: 'Various'
                        },
                        created: '2018-12-31T12:00:00+00:00',
                        views: 1315,
                        answers: 2,
                        canVote: true
                    },
                    {
                        id: 3,
                        title: 'What\'s the meaning of life?',
                        description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.',
                        score: 32,
                        category: {
                            id: 1,
                            name: 'Various'
                        },
                        created: '2018-12-31T12:00:00+00:00',
                        views: 1315,
                        answers: 2,
                        canVote: true
                    }
                ]
            }
        }
    }
</script>

<style scoped>
    .QuestionsList {

    }
</style>