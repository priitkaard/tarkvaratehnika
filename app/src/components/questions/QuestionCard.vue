<template>
    <div class="QuestionCard noselect" v-if="question">
        <question-card-vote
                :score="score"
                :disable-voting="disableVoting"
                @onUpVote="upVote(question.id)"
                @onDownVote="downVote(question.id)"
        />

        <div class="QuestionCard__body" @click="$emit('onContentClick')">
            <h3>{{ question.title }}</h3>
            <p v-html="question.text"></p>
        </div>

        <div class="QuestionCard__details">
            <div class="detail" v-if="views">
                <eye-icon :size="20"/>
                {{ views }}
            </div>

            <div class="detail" v-if="comments !== undefined">
                <comment-icon :size="20"/>
                {{ comments }}
            </div>

            <div class="detail" v-if="question.user">
                <account-icon />
                {{ question.user.username }}
            </div>

            <div class="detail" v-if="created">
                <clock-outline-icon :size="20"/>
                {{ created }}
            </div>

            <div class="detail" v-if="question.category">
                <folder-icon :size="20"/>
                {{ question.category.name }}
            </div>

            <div class="detail" v-if="isLoggedIn && isDetailView">
                <UIButton text="Comment" @click="$emit('onCommentClick')"/>
            </div>

            <div class="detail" v-if="isLoggedIn && isCurrentUser(question.user) && isDetailView">
                <UIButton text="Edit" @click="toggleIsEditAreaVisible()"/>
            </div>
        </div>

        <div v-if="isCurrentUser(question.user) && isEditAreaVisible">
                <UITextArea :value.sync="editInput" />
                <UIButton text="Edit" @click="updateQuestionText()"/>
        </div>
    </div>
</template>

<script>
    import QuestionCardVote from './QuestionCardVote';
    import UIButton from '../common/UIButton';
    import FolderIcon from 'vue-material-design-icons/Folder';
    import ClockOutlineIcon from 'vue-material-design-icons/ClockOutline';
    import EyeIcon from 'vue-material-design-icons/Eye';
    import CommentIcon from 'vue-material-design-icons/Comment';
    import moment from 'moment';
    import { mapState } from 'vuex';
    import AccountIcon from 'vue-material-design-icons/Account';
    import UITextArea from '../common/UITextArea';
    import questionService from '../../services/QuestionService';
    import { isCurrentUser } from '../../services/AuthService';

    export default {
        name: 'QuestionCard',
        components: {
            AccountIcon,
            QuestionCardVote,
            FolderIcon,
            ClockOutlineIcon,
            EyeIcon,
            CommentIcon,
            UIButton,
            UITextArea,
        },
        props: {
            'question': Object,
            'views': Number,
            'comments': Number,
            isDetailView: Boolean,
        },
        computed: {
            ...mapState('auth', ['isLoggedIn', 'username']),

            score() {
                return questionService.calculateScore(this.question);
            },

            created() {
                if (this.question && this.question.created) {
                    return moment(this.question.created).fromNow(true);
                }
                return null;
            },

            disableVoting() {
                return !this.isLoggedIn || !this.question || questionService.hasVoted(this.question);
            },
        },
        methods: {
            isCurrentUser,

            toggleIsEditAreaVisible() {
                this.isEditAreaVisible = !this.isEditAreaVisible;
            },

            async updateQuestionText() {
                this.toggleIsEditAreaVisible();

                const payload = {
                    text: this.editInput,
                    title: this.question.title,
                    categoryId: this.question.category.id,
                };
                await questionService.updateQuestion(this.question.id, payload);

                this.requestQuestionReload();
            },

            requestQuestionReload() {
                this.$emit('onRequestQuestionReload');
            },

            addVoteToQuestion(vote) {
                this.question.votes.push(vote);
            },

            async upVote() {
                const vote = await questionService.upVote(this.question.id);
                this.addVoteToQuestion(vote);
            },

            async downVote() {
                const vote = await questionService.downVote(this.question.id);
                this.addVoteToQuestion(vote);
            },
        },
        data() {
            return {
                isEditAreaVisible: false,
                editInput: String(this.question.text),
            };
        },
    };
</script>

<style scoped lang="scss">
    @import '../../assets/styles/colors';
    @import '../../assets/styles/mixins';

    .QuestionCard {
        width: 100%;
        padding: 20px;
        background-color: white;
        min-height: 150px;
        margin: 10px 0;
        position: relative;
        @include shadow-box;

        &__body {
            display: inline-block;
            width: calc(100% - 40px);
            padding: 10px;
            vertical-align: top;
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
            right: 20px;
            height: 40px;
            line-height: 40px;

            .detail {
                display: inline-flex;
                align-items: center;
                font-size: 14px;
                margin-left: 20px;
                .material-design-icon { margin-right: 5px }
                .UIButton { margin-right: -20px }
            }
            @media (max-width: 768px) {
                position: relative;
                height: auto;
                line-height: 20px;
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
