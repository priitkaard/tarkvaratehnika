<template>
    <div class="QuestionsList">
        <QuestionsListElement
                v-for="question in questions"
                v-bind:key="question.id"
                v-bind:question="question" />
    </div>
</template>

<script>
    import QuestionsListElement from './QuestionsListElement';
    import {mapGetters} from 'vuex';

    export default {
        name: "QuestionsList",
        components: {
            QuestionsListElement
        },
        computed: {
            ...mapGetters('question', ['questions'])
        }
    }
</script>

<style scoped>
    .QuestionsList {
        margin-top: -10px;
    }
</style>