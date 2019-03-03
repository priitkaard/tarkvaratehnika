<template>
    <div class="QuestionsList">
        <QuestionsListElement
                v-for="question in questions"
                v-bind:key="question.id"
                v-bind:question="question" />
        <div v-if="questions.length === 0"
            class="QuestionsList__no_results">
            <div>
                <img src="@/assets/img/travolta.gif" height="100" />
            </div>
            <div class="QuestionsList__no_results_text">
                No questions found for your search...
            </div>
        </div>
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

<style lang="scss" scoped>
    @import '../../../assets/styles/_colors';
    @import '../../../assets/styles/_mixins';

    .QuestionsList {
        margin-top: -10px;

        &__no_results {
            padding: 20px;
            text-align: center;
            background-color: white;
            color: black;
            margin-top: 10px;
            @include shadow-box;
            &_text {
                margin-top: 10px;
            }
        }
    }
</style>