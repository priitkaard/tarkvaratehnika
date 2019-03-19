<template>
    <div class="QuestionsList">
        <QuestionsListElement
                v-for="question in questions.questions"
                v-bind:key="question.id"
                v-bind:question="question" />

        <div v-if="questions.questions.length === 0"
            class="QuestionsList__no_results">
            <div>
                <img src="../../../assets/img/travolta.gif" height="100" />
            </div>
            <div class="QuestionsList__no_results_text">
                No questions found for your search...
            </div>
        </div>

        <div class="QuestionsList__pagination">
            <UIButton
                    class="QuestionsList__pagination_previous"
                    text="Previous"
                    v-if="filters.page > 0"
                    v-on:click="updatePage(filters.page - 1)" />
            <UIButton
                    class="QuestionsList__pagination_next"
                    text="Next"
                    v-if="filters.page < questions.totalPages - 1"
                    v-on:click="updatePage(filters.page + 1)" />
        </div>
    </div>
</template>

<script>
    import QuestionsListElement from './QuestionsListElement';
    import UIButton from '../../common/UIButton';
    import {mapGetters} from 'vuex';

    export default {
        name: "QuestionsList",
        components: {
            QuestionsListElement,
            UIButton
        },
        computed: {
            ...mapGetters('question', ['questions', 'filters'])
        },
        methods: {
            updatePage(page) {
                window.scrollTo(0,0);
                this.$store.dispatch('question/updatePage', page);
            }
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
        &__pagination {
            &_previous { float: left }
            &_next { float: right }
        }
    }
</style>