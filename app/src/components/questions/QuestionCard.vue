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
            <div class="detail" v-if="question.category">
                <folder-icon :size="20"/>
                {{ question.category.name }}
            </div>
            <div class="detail" v-if="created">
                <clock-outline-icon :size="20"/>
                {{ created }}
            </div>
            <div class="detail" v-if="views">
                <eye-icon :size="20"/>
                {{ views }} view(s)
            </div>
            <div class="detail" v-if="comments !== undefined">
                <comment-icon :size="20"/>
                {{ comments }} comments
            </div>
        </div>
    </div>
</template>

<script>
    import QuestionCardVote from "./QuestionCardVote";
    import FolderIcon from 'vue-material-design-icons/Folder';
    import ClockOutlineIcon from 'vue-material-design-icons/ClockOutline';
    import EyeIcon from 'vue-material-design-icons/Eye';
    import CommentIcon from 'vue-material-design-icons/Comment';
    import moment from 'moment';
    import {mapActions} from "vuex";

    export default {
        name: "QuestionCard",
        components: {QuestionCardVote, FolderIcon, ClockOutlineIcon, EyeIcon, CommentIcon},
        props: {
            'question': Object,
            'views': Number,
            'comments': Number
        },
        computed: {
            created() {
                if (this.question && this.question.created) {
                    return moment(this.question.created).fromNow();
                }
                return null;
            }
        },
        methods: {
            ...mapActions('question', ['upVote', 'downVote']),
        },
    }
</script>

<style scoped lang="scss">
    @import '../../assets/styles/colors';
    @import '../../assets/styles/mixins';

    .QuestionCard {
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