<template>
    <div class="QuestionFilterBar">
        <div class="QuestionFilterBar__option"
             v-for="option in options"
             v-bind:class="{'QuestionFilterBar__option_active': current === option}"
             v-bind:key="option.key"
             v-on:click="onClick(option)">{{ option.name }}</div>
    </div>
</template>

<script>
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
                ],
                current: null,
            }
        },
        created() {
            this.current = this.options[0];
        },
        methods: {
            onClick(option) {
                if (this.current === option) {
                    return;
                }
                this.current = option;
                this.$emit('sort-by-changed', option.key);
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
            }

            &:hover {
                cursor: pointer;
                background-color: $color-background;
                transition: 0.4s background-color;
                &:first-child {
                    background-color: $color-primary-darker;
                }
            }
        }
        @media (max-width: 1024px) {
            flex-direction: column;
        }
    }
</style>