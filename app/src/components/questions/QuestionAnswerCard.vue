<template>
    <div :class="classNames('QuestionAnswerCard', 'noselect', { 'QuestionAnswerCard--accepted': answer.accepted })"
         v-if="answer"
    >
        <question-card-vote
                :score="score"
                :disable-voting="disableVoting"
                @onUpVote="upVoteAnswer()"
                @onDownVote="downVoteAnswer()"
        />

        <div class="QuestionAnswerCard__body">
            <p v-html="answer.text"></p>
        </div>

        <div class="QuestionAnswerCard__details">
            <div class="detail">
                <account-icon />
                {{ answer.user.username }}
            </div>

            <div class="detail" v-if="created">
                <clock-outline-icon :size="20"/>
                {{ created }}
            </div>

            <div class="detail">
                <UIButton text="Comment" @click="toggleComment()" v-if="isLoggedIn"/>
            </div>

            <div class="detail no-margin" v-if="isLoggedIn && isCurrentUser(answer.user)">
                <UIButton text="Edit" @click="toggleIsEditVisible()"/>
            </div>

            <div class="detail no-margin" v-if="isLoggedIn && isCurrentUser(question.user) && !findBestAnswer()">
                <UIButton text="Best" @click="chooseBestAnswer()" />
            </div>
        </div>

        <div v-if="isCurrentUser(answer.user) && isEditVisible">
            <UITextArea :value.sync="editInput" />
            <UIButton text="Edit" @click="updateAnswerText()" />
        </div>
    </div>
</template>

<script>
    import QuestionCardVote from './QuestionCardVote';
    import UITextArea from '../common/UITextArea';
    import UIButton from '../common/UIButton';
    import ClockOutlineIcon from 'vue-material-design-icons/ClockOutline';
    import moment from 'moment';
    import { mapState } from 'vuex';
    import AccountIcon from 'vue-material-design-icons/Account';
    import questionService from '../../services/QuestionService';
    import { isCurrentUser } from '../../services/AuthService';
    import classNames from 'classnames';

    export default {
        name: 'QuestionAnswerCard',
        components: { AccountIcon, QuestionCardVote, ClockOutlineIcon, UIButton, UITextArea },
        props: {
            question: Object,
            answer: Object,
        },

        computed: {
            ...mapState('auth', ['isLoggedIn', 'username']),

            created() {
                if (this.answer && this.answer.created) {
                    return moment(this.answer.created).fromNow();
                }
                return null;
            },

            score() {
                return questionService.calculateScore(this.answer);
            },

            disableVoting() {
                return !this.isLoggedIn || !this.answer || questionService.hasVoted(this.answer);
            },
        },

        methods: {
            classNames,
            isCurrentUser,

            findBestAnswer() {
                return this.question && this.question.answers.find(answer => answer.accepted);
            },

            toggleIsEditVisible() {
                this.isEditVisible = !this.isEditVisible;
            },

            async updateAnswerText() {
                this.toggleIsEditVisible();

                await questionService.updateAnswer(this.answer.id, { text: this.editInput });
                this.requestQuestionReload();
            },

            requestQuestionReload() {
                this.$emit('onRequestQuestionReload');
            },

            toggleComment() {
                this.$emit('onCommentClick');
            },

            addVoteToAnswer(vote) {
                this.answer.votes.push(vote);
            },

            async upVoteAnswer() {
                const vote = await questionService.upVoteAnswer(this.answer.id);
                this.addVoteToAnswer(vote);
            },

            async downVoteAnswer() {
                const vote = await questionService.downVoteAnswer(this.answer.id);
                this.addVoteToAnswer(vote);
            },

            async chooseBestAnswer() {
                const message =
                    'Do you really want to select this answer as best answer? This action can not be reverted!';

                if (confirm(message)) {
                    await questionService.acceptAnswer(this.answer.id);
                }
                this.requestQuestionReload();
            },
        },
        data() {
            return {
                isEditVisible: false,
                editInput: String(this.answer.text),
            };
        },
    };
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

        &--accepted {
            border-top: 8px solid #00b900;
        }

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
                &.no-margin { margin-left: 0 }

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
