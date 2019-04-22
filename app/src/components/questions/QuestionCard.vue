<template>
    <div class="QuestionCard noselect" v-if="question">
        <question-card-vote
                :score="question.score"
                :disable-voting="!question.canVote"
                @onUpVote="upVote(question.id)"
                @onDownVote="downVote(question.id)" />

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
            <div class="detail" v-if="commentButton && isLoggedIn">
                <UIButton text="Comment" @click="$emit('onCommentClick')"/>
            </div>
        </div>
        <div v-if="question.user.username === currentUser">
                KAMEHAHAHAHAHAHHA
                <UITextField :value = "question.text" full />
                <UIButton text="Edit" @click="updateQuestion(newText)"/>
            </div>
    </div>
</template>

<script>
    import QuestionCardVote from "./QuestionCardVote";
    import UIButton from "../common/UIButton";
    import FolderIcon from 'vue-material-design-icons/Folder';
    import ClockOutlineIcon from 'vue-material-design-icons/ClockOutline';
    import EyeIcon from 'vue-material-design-icons/Eye';
    import CommentIcon from 'vue-material-design-icons/Comment';
    import moment from 'moment';
    import {mapActions, mapState} from "vuex";
    import AccountIcon from "vue-material-design-icons/Account";
    import UITextField from '../common/UITextField';

    export default {
        name: "QuestionCard",
        components: {AccountIcon, QuestionCardVote, FolderIcon, ClockOutlineIcon, EyeIcon, CommentIcon, UIButton, UITextField},
        props: {
            'question': Object,
            'views': Number,
            'comments': Number,
            commentButton: Boolean,
        },
        computed: {
            ...mapState('auth', ['isLoggedIn']),
            created() {
                if (this.question && this.question.created) {
                    return moment(this.question.created).fromNow(true);
                }
                return null;
            }
        },
        methods: {
            ...mapActions('question', ['upVote', 'downVote']),
             async updateQuestion(newText) {
                await questionService.updateQuestion(this.question.id, {title: this.question.title, text: newText, categoryId: this.question.categoryId});
                await this.loadQuestion();
            },
        },
        data() {
            return { 
                 currentUser: localStorage.getItem('username')
            }
        }
    }
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