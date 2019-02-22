<template>
    <div class="QuestionsListElement noselect">
        <div class="score">
            <menu-up-icon v-if="question.canVote" class="icon-button" :size="40" v-on:click="vote('UP')"/>
            <em v-bind:class="{ positive: question.score > 0, negative: question.score < 0 }">{{ question.score }}</em>
            <menu-down-icon v-if="question.canVote" class="icon-button" :size="40" v-on:click="vote('DOWN')"/>
        </div>

        <div class="body" v-on:click="openQuestion">
            <h3>{{ question.title }}</h3>
            <p v-html="question.text"></p>
        </div>

        <QuestionsListElementDetails/>

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
        },
        methods: {
            openQuestion() {
                this.$router.push({
                    name: 'QuestionView',
                    params: {
                        id: this.question.id
                    }
                });
            },

            vote(direction) {
                this.$emit('vote', {
                    id: this.question.id,
                    direction
                });
            }
        }
    }
</script>

<style lang="scss" scoped>
    @import '../../../assets/styles/colors';

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
                width: 40px;
                &.positive {
                    color: $color-green
                }
                &.negative {
                    color: $color-red
                }
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
            &:hover {
                cursor: pointer;
            }
        }
    }
</style>