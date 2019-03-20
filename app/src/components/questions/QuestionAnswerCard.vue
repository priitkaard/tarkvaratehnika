<template>
    <div class="QuestionAnswerCard noselect" v-if="answer">
        <question-card-vote
                :score="answer.score"
                :disable-voting="!answer.canVote"
                @onUpVote="upVoteAnswer(answer.id)"
                @onDownVote="downVoteAnswer(answer.id)" />

        <div class="QuestionAnswerCard__body">
            <h3>{{ answer.title }}</h3>
            <p v-html="answer.text"></p>
        </div>

        <div class="QuestionAnswerCard__details">
            <div class="detail" v-if="created">
                <clock-outline-icon :size="20"/>
                {{ created }}
            </div>
        </div>
    </div>
</template>

<script>
    import QuestionCardVote from "./QuestionCardVote";
    import ClockOutlineIcon from 'vue-material-design-icons/ClockOutline';
    import moment from 'moment';
    import {mapActions} from "vuex";

    export default {
        name: "QuestionAnswerCard",
        components: {QuestionCardVote, ClockOutlineIcon},
        props: {
            'answer': Object,
        },
        computed: {
            created() {
                if (this.answer && this.answer.created) {
                    return moment(this.answer.created).fromNow();
                }
                return null;
            }
        },
        methods: {
            ...mapActions('question', ['upVoteAnswer', 'downVoteAnswer']),
        },
    }
</script>

<style scoped lang="scss">
    @import '../../assets/styles/colors';
    @import '../../assets/styles/mixins';

    .QuestionAnswerCard {
        display: flex;
        width: 100%;
        padding: 20px;
        background-color: white;
        min-height: 150px;
        margin: 10px 0;
        position: relative;
        @include shadow-box;

        &__body {
            display: inline-block;
            width: 100%;
            padding: 10px;
            vertical-align: middle;
            h3 {
                font-size: 20px;
                white-space: nowrap;
                overflow: hidden;
                text-overflow: ellipsis;
            }
            p {
                font-size: 14px;
                overflow: hidden;
                display: -webkit-box;
                -webkit-line-clamp: 3;
                -webkit-box-orient: vertical;
            }
            &:hover { cursor: pointer }
        }
        &__details {
            position: absolute;
            bottom: 10px;
            right: 20px;

            .detail {
                display: inline-flex;
                align-items: center;
                font-size: 14px;
                margin-left: 20px;
                .material-design-icon { margin-right: 5px }
            }
            @media (max-width: 1000px) {
                position: initial;
                .detail {
                    display: inline-block;
                    width: 50%;
                    margin-left: 0;
                    text-align: center;
                    margin-top: 10px;
                    font-size: 12px;
                }
            }
        }
    }
</style>