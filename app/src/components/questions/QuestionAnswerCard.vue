<template>
    <div class="QuestionAnswerCard noselect" v-if="answer">
        <question-card-vote
                :score="answer.score"
                :disable-voting="disableVoting"
                @onUpVote="upVoteAnswer()"
                @onDownVote="downVoteAnswer()" />

        <div class="QuestionAnswerCard__body">
            <p v-html="answer.text"></p>
        </div>

        <div class="QuestionAnswerCard__details">
            <div class="detail" v-if="answer.user">
                <account-icon />
                {{ answer.user.username }}
            </div>
            <div class="detail" v-if="created">
                <clock-outline-icon :size="20"/>
                {{ created }}
            </div>
            <div class="detail">
                <UIButton text="Comment" @click="$emit('onCommentClick')" v-if="isLoggedIn"/>
            </div>
        </div>
    </div>
</template>

<script>
    import QuestionCardVote from "./QuestionCardVote";
    import UIButton from "../common/UIButton";
    import ClockOutlineIcon from 'vue-material-design-icons/ClockOutline';
    import moment from 'moment';
    import { mapState } from "vuex";
    import AccountIcon from "vue-material-design-icons/Account";
    import questionService from "../../services/QuestionService";

    export default {
        name: "QuestionAnswerCard",
        components: {AccountIcon, QuestionCardVote, ClockOutlineIcon, UIButton},
        props: {
            'answer': Object,
        },
        computed: {
            ...mapState('auth', ['isLoggedIn', 'username']),
            created() {
                if (this.answer && this.answer.created) {
                    return moment(this.answer.created).fromNow();
                }
                return null;
            },
            disableVoting() {
                return !this.answer || questionService.hasVoted(this.answer);
            }
        },
        methods: {
            addVoteToAnswer(vote) {
                this.answer.votes.push(vote);
            },
            async upVoteAnswer() {
                const vote = await questionService.upVoteAnswer(this.answer.id);
                this.addVoteToAnswer(vote);
                this.answer.score += 1;
            },
            async downVoteAnswer() {
                const vote = await questionService.downVoteAnswer(this.answer.id);
                this.addVoteToAnswer(vote);
                this.answer.score -= 1;
            }
        },
    }
</script>

<style scoped lang="scss">
    @import '../../assets/styles/colors';
    @import '../../assets/styles/mixins';

    .QuestionAnswerCard {
        width: 100%;
        padding: 20px;
        background-color: white;
        min-height: 100px;
        margin: 10px 0;
        position: relative;
        @include shadow-box;

        &__body {
            display: inline-block;
            width: calc(100% - 40px);
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
            bottom: 0;
            right: 0;

            .detail {
                display: inline-flex;
                align-items: center;
                font-size: 14px;
                margin-left: 20px;
                .material-design-icon { margin-right: 5px }
            }
            @media (max-width: 768px) {
                position: initial;
                .detail {
                    display: block;
                    width: 100%;
                    margin-left: 0;
                    text-align: center;
                    margin-top: 10px;
                    font-size: 12px;
                }
            }
        }
    }
</style>