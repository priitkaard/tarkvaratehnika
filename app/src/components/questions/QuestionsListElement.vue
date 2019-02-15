<template>
    <div class="QuestionsListElement">
        <div class="score">
            <menu-up-icon class="icon-button" :size="40" />
            <em v-bind:class="{ positive: question.score > 0, negative: question.score < 0 }">{{ question.score }}</em>
            <menu-down-icon class="icon-button" :size="40" />
        </div>

        <div class="body">
            <h3>{{ question.title }}</h3>
            <p>{{ question.description }}</p>
        </div>

        <QuestionsListElementDetails />

    </div>
</template>

<script>
    import MenuUpIcon from "vue-material-design-icons/MenuUp.vue"
    import MenuDownIcon from "vue-material-design-icons/MenuDown.vue"
    import QuestionsListElementDetails from './QuestionsListElementDetails';

    export default {
        name: "QuestionsListElement",
        props: ['question'],
        components: {
            MenuUpIcon,
            MenuDownIcon,
            QuestionsListElementDetails
        }
    }
</script>

<style lang="scss" scoped>
    @import '../../assets/styles/_colors.scss';

    .QuestionsListElement {
        width: 100%;
        padding: 20px;
        background-color: white;
        min-height: 150px;
        margin: 10px 0;
        position: relative;

        .score {
            display: inline-flex;
            flex-direction: column;
            vertical-align: middle;
            text-align: center;

            em {
                font-style: normal;
                font-weight: bold;
                &.positive { color: $color-green }
                &.negative { color: $color-red }
            }
        }

        .body {
            display: inline-block;
            width: calc(100% - 40px);
            padding: 10px;
            vertical-align: middle;
            h3 {
                font-size: 20px;
            }
            p {
                font-size: 14px;
            }
        }
    }
</style>