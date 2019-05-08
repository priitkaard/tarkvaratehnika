<template>
    <div class="QuestionsList">
        <question-card
                v-for="question in questions.questions"
                :key="question.id"
                :question="question"
                :comments="question.comments"
                :views="question.views"
                @onContentClick="openQuestionView(question.id)" />

        <div v-if="questions.questions.length === 0"
             class="QuestionsList__no_results"
        >
            <div>
                <img src="../../../assets/img/travolta.gif" height="100"  alt="No questions found" />
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
    import UIButton from '../../common/UIButton';
    import { mapGetters } from 'vuex';
    import QuestionCard from '../QuestionCard';

    export default {
        name: 'QuestionsList',
        components: {
            QuestionCard,
            UIButton,
        },
        computed: {
            ...mapGetters('question', ['questions', 'filters']),
        },
        methods: {
            updatePage(page) {
                window.scrollTo(0, 0);
                this.$store.dispatch('question/updatePage', page);
            },
            openQuestionView(questionId) {
                this.$router.push({ name: 'QuestionDetailView', params: { id: questionId } });
            },
        },
    };
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
