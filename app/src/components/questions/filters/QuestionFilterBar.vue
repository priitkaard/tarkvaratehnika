<template>
    <div class="QuestionFilterBar">
        <div class="QuestionFilterBar__option"
             v-for="option in options"
             v-bind:class="{'QuestionFilterBar__option_active': filters.sort === option.key}"
             v-bind:key="option.key"
             v-on:click="onClick(option.key)">{{ option.name }}</div>
    </div>
</template>

<script>
    import {mapGetters, mapActions} from 'vuex';

    export default {
        name: "QuestionFilterBar",
        data() {
            return {
                options: [
                    {key: 'score', name: 'Most popular'},
                    {key: 'answers', name: 'Most responses'},
                    {key: 'last_answer', name: 'Recently answered'},
                    {key: 'comments', name: 'Most comments'},
                    {key: 'last_comment', name: 'Recently commented'}
                ]
            }
        },
        computed: {
            ...mapGetters('question', ['filters'])
        },
        methods: {
            ...mapActions('question', ['updateSort', 'updateQuestionList']),
            onClick(sort) {
                if (this.filters.sort !== sort) {
                    this.updateSort(sort);
                }
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '../../../assets/styles/colors';
    @import '../../../assets/styles/mixins';

    .QuestionFilterBar {
        @include shadow-box;

        $questions-sort-bar-height: 50px;

        display: flex;
        width: 100%;
        background-color: white;
        margin-bottom: 10px;
        border-top: 2px solid $color-primary;
        overflow: hidden;

        &__option {
            display: inline-block;
            height: $questions-sort-bar-height;
            line-height: $questions-sort-bar-height;
            width: 100%;
            text-align: center;
            vertical-align: middle;
            padding: 0 20px;
            transition: 0.4s background-color;

            &_active {
                background-color: $color-primary;
                color: white;
                font-weight: bold;
                &:hover {
                    background-color: $color-primary-darker;
                }
            }

            &:hover {
                cursor: pointer;
                background-color: $color-background;
                transition: 0.4s background-color;
            }
        }
        @media (max-width: 1024px) {
            flex-direction: column;
        }
    }
</style>