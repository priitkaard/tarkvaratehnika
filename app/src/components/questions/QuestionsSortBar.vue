<template>
    <div class="QuestionsSortBar">
        <div class="option" v-for="option in options" v-bind:key="option">{{ option }}</div>
    </div>
</template>

<script>
    export default {
        name: "QuestionsSortBar",
        data() {
            return {
                options: [
                    'Most popular',
                    'Most responses',
                    'Recently answered',
                    'Most views',
                    'Recently viewed',
                    'No answers'
                ]
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '../../assets/styles/_colors.scss';
    @import '../../assets/styles/_mixins.scss';

    .QuestionsSortBar {
        @include shadow-box;

        $questions-sort-bar-height: 50px;

        display: flex;
        width: 100%;
        background-color: white;
        margin-bottom: 10px;
        border-top: 2px solid $primary-color;
        overflow: hidden;

        .option {
            display: inline-block;
            height: $questions-sort-bar-height;
            line-height: $questions-sort-bar-height;
            width: 100%;
            text-align: center;
            vertical-align: middle;
            padding: 0 20px;
            transition: 0.4s background-color;

            &:first-child {
                background-color: $primary-color;
                color: white;
                font-weight: bold;
            }

            &:hover {
                cursor: pointer;
                background-color: $background-color;
                transition: 0.4s background-color;
                &:first-child {
                    background-color: $primary-color-darker;
                }
            }
        }
        @media (max-width: 1024px) {
            flex-direction: column;
        }
    }
</style>