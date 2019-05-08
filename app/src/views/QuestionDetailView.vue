<template>
    <div class="QuestionDetailView container" v-if="question">
        <div>
            <question-card
                :question="question"
                @onCommentClick="toggleComment('question')"
                @onRequestQuestionReload="loadQuestion()"
                comment-button
                isDetailView
            />

            <div class="QuestionDetailView__comment_wrapper">
                <question-comment-card
                        v-for="comment in question.comments"
                        :key="comment.id"
                        :comment="comment"
                />

                <form @submit.prevent="commentQuestion()" v-if="commentDisplay.question">
                    <UIGroup class="mt-2">
                        <UITextField :value.sync="commentInputs.question" placeholder="Comment" full />
                        <UIButton text="Comment" @click="commentQuestion()" />
                    </UIGroup>
                </form>
            </div>
        </div>

        <h4 class="mt-5">Answers</h4>

        <p v-if="question.answers && question.answers.length < 1">No answers yet</p>

        <UISelect :options="sortOptions"
                  :value.sync="sortBy"
                  @onChange="sortAnswers()"
                  v-if="question.answers && question.answers.length > 0" />

        <div v-for="answer in sortedAnswers"
             :key="answer.id"
        >
            <question-answer-card
                    :question="question"
                    :answer="answer"
                    @onCommentClick="toggleComment(answer.id)"
                    @onRequestQuestionReload="loadQuestion()"
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

        <div v-if="isLoggedIn && !bestAnswer">
            <h4 class="mt-5">Answer the question</h4>

            <form @submit.prevent="answerQuestion()">
                <UITextArea :value.sync="answerInput" />
                <UIButton text="Answer" @click="answerQuestion()" />
            </form>
        </div>
    </div>
</template>

<script>
    import UISelect from '../components/common/UISelect';
    import UITextArea from '../components/common/UITextArea';
    import UIButton from '../components/common/UIButton';
    import UITextField from '../components/common/UITextField';
    import UIGroup from '../components/common/UIGroup';
    import questionService from '../services/QuestionService';
    import QuestionCard from '../components/questions/QuestionCard';
    import QuestionAnswerCard from '../components/questions/QuestionAnswerCard';
    import QuestionCommentCard from '../components/questions/QuestionCommentCard';
    import { mapState } from 'vuex';

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
                sortedAnswers: [],
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
                newText: '',
            };
        },
        computed: {
            ...mapState('auth', ['isLoggedIn']),

            bestAnswer() {
                return this.question && this.question.answers.find(answer => answer.accepted);
            },
        },
        methods: {
            async loadQuestion() {
                this.question = await questionService.getQuestion(this.$route.params.id);

                this.commentDisplay = { question: false };
                this.commentInputs = { question: '' };

                this.question.answers.forEach(answer => {
                    this.commentDisplay[answer.id] = false;
                    this.commentInputs[answer.id] = '';
                });

                this.sortedAnswers = this.question.answers;
                this.sortAnswers();
            },

            sortAnswers() {
                this.sortedAnswers = this.sortedAnswers.sort((a1, a2) => {
                    if (a1.accepted) {
                        return -1;
                    }
                    if (a2.accepted) {
                        return 1;
                    }
                    if (this.sortBy && this.sortBy.name === 'Score') {
                        return questionService.calculateScore(a1) > questionService.calculateScore(a2) ? -1 : 1;
                    } else if (this.sortBy && this.sortBy.name === 'Time') {
                        return a1.created > a2.created ? -1 : 1;
                    }
                    return -1;
                });
            },

            async answerQuestion() {
                await questionService.answerQuestion(this.question.id, this.answerInput);
                await this.loadQuestion();
                this.answerInput = '';
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
                    [key]: !this.commentDisplay[key],
                };
            },
            async updateQuestion(newText) {
                const payload = {
                    text: newText,
                    title: this.question.title,
                    categoryId: this.question.category.id,
                };
                await questionService.updateQuestion(this.question.id, payload);
                await this.loadQuestion();
            },
            async updateAnswer(newData) {
                await questionService.updateAnswer(newData.id, { text: newData.new });
                await this.loadQuestion();
            },
            async chooseBestAnswer(id){
                if(confirm('Do you really want to select this answer as best answer? (Permanent)')) {
                    await questionService.acceptAnswer(id);
                    await this.loadQuestion();
                }
                await this.loadQuestion();
            },
        },
        async created() {
            await this.loadQuestion();
            this.sortBy = this.sortOptions[0];
        },
    };
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
