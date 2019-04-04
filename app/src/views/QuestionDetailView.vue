<template>
    <div class="QuestionDetailView container" v-if="question">
        <div>
            <question-card :question="question" @onCommentClick="toggleComment('question')" comment-button />

            <div class="QuestionDetailView__comment_wrapper">
                <question-comment-card
                        v-for="comment in question.comments"
                        :key="comment.id"
                        :comment="comment" />
                <form @submit.prevent="commentQuestion()" v-if="commentDisplay.question">
                    <UIGroup class="mt-2">
                        <UITextField :value.sync="commentInputs.question" placeholder="Comment" full />
                        <UIButton text="Comment" @click="commentQuestion()" />
                    </UIGroup>
                </form>
            </div>
        </div>

        <h4 class="mt-5">Answers</h4>
        <p v-if="question.answers.length < 1">No answers yet</p>
        <UISelect :options="sortOptions"
                  :value.sync="sortBy"
                  v-if="question.answers && question.answers.length > 0" />

        <div v-for="answer in question.answers"
             :key="answer.id"
        >
            <question-answer-card
                    :answer="answer"
                    @onCommentClick="toggleComment(answer.id)"
            />

            <div class="QuestionDetailView__comment_wrapper">
                <question-comment-card
                        v-for="comment in answer.comments"
                        :key="comment.id"
                        :comment="comment" />

                <form @submit.prevent="commentAnswer(answer.id)" v-if="commentDisplay[answer.id]">
                    <UIGroup class="mt-2">
                        <UITextField :value.sync="commentInputs[answer.id]" placeholder="Comment" full />
                        <UIButton text="Comment" @click="commentAnswer(answer.id)" />
                    </UIGroup>
                </form>
            </div>

        </div>

        <h4 class="mt-5" v-if="isLoggedIn">Answer the question</h4>

        <form @submit.prevent="answerQuestion()" v-if="isLoggedIn">
            <UITextArea :value.sync="answerInput" />
            <UIButton text="Answer" @click="answerQuestion()" />
        </form>
    </div>
</template>

<script>
    import UISelect from '../components/common/UISelect';
    import UITextArea from '../components/common/UITextArea';
    import UIButton from '../components/common/UIButton';
    import UITextField from '../components/common/UITextField';
    import UIGroup from '../components/common/UIGroup';
    import questionService from '../services/QuestionService';
    import QuestionCard from "../components/questions/QuestionCard";
    import QuestionAnswerCard from "../components/questions/QuestionAnswerCard";
    import QuestionCommentCard from "../components/questions/QuestionCommentCard";
    import {mapState} from "vuex";

    export default {
        name: 'QuestionDetailView',
        components: {
            QuestionCard,
            QuestionAnswerCard,
            QuestionCommentCard,
            UISelect,
            UITextArea,
            UIButton,
            UITextField,
            UIGroup,
        },
        data () {
            return {
                question: null,
                answerInput: '',
                commentDisplay: {},
                commentInputs: {},
                sortOptions: [
                    {
                        id: 1,
                        name: 'Score',
                    },
                    {
                        id: 2,
                        name: 'Time',
                    },
                ],
                sortBy: null,
            }
        },
        computed: {
            ...mapState('auth', ['isLoggedIn']),
        },
        methods: {
            async loadQuestion() {
                this.question = await questionService.getQuestion(this.$route.params.id);

                this.commentDisplay = { question: false };
                this.commentInputs = { question: '' };

                this.question.answers.forEach(answer => {
                    this.commentDisplay[answer.id] = false;
                    this.commentInputs[answer.id] = '';
                })
            },
            async answerQuestion() {
                await questionService.answerQuestion(this.question.id, this.answerInput);
                await this.loadQuestion();
                this.answerInput = ''
            },
            async commentAnswer(answerId) {
                await questionService.commentAnswer(answerId, this.commentInputs[answerId]);
                this.commentInputs[answerId] = '';
                this.commentDisplay[answerId] = false;
                await this.loadQuestion();
            },
            async commentQuestion() {
                await questionService.commentQuestion(this.question.id, this.commentInputs.question);
                this.commentInputs.question = '';
                this.commentDisplay.question = false;
                await this.loadQuestion();
            },
            toggleComment(key) {
                this.commentDisplay = {
                    ...this.commentDisplay,
                    [key]: !this.commentDisplay[key]
                };
            }
        },
        async created() {
            await this.loadQuestion();
            this.sortBy = this.sortOptions[0];
        },
    }
</script>

<style scoped lang="scss">
    .QuestionDetailView {
        margin-bottom: 20px;

        &__comment_wrapper {
            padding: 0 20px;
        }
        .UITextArea, .UISelect {
            margin: 10px 0;
        }
        @media (max-width: 768px) {
            .UISelect { width: 100% }
        }
    }
</style>
